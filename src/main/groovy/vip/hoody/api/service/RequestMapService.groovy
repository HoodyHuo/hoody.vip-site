package vip.hoody.api.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vip.hoody.api.domain.RequestMap
import vip.hoody.api.repository.RequestMapRepository

@Service
class RequestMapService {

    @Autowired
    RequestMapRepository requestMapRepository

    List<RequestMap> findAllRequestMap() {
        return requestMapRepository.findAll()
    }
}
