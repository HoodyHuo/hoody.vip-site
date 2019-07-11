package vip.hoody.api.service.impl


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import vip.hoody.api.exception.StorageException
import vip.hoody.api.exception.StorageFileNotFoundException
import vip.hoody.api.service.StorageService

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

@Profile(["dev", "prod","ecs"])
@Service
class FileSystemStorageService implements StorageService {

    private Path rootLocation;

    @Autowired
    public FileSystemStorageService(@Value('${platform.uploadDir}') String uploadDir) {
        println("uploadDir:${uploadDir}")
        this.rootLocation = Paths.get(uploadDir)
    }

    @Override
    void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter { Path path -> !path.equals(this.rootLocation) }
                    .map { path -> this.rootLocation.relativize(path) }
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
