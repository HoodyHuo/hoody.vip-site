package vip.hoody.api.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vip.hoody.api.domain.User
import vip.hoody.api.repository.UserRepository
import vip.hoody.api.util.ResponseData

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    UserRepository userRepository

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的username来获取用户详细信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户账户名", required = true, dataType = "String")
    @GetMapping("/info/{username}")
    ResponseData userRegister(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username)
        return new ResponseData(data: user)
    }
}
