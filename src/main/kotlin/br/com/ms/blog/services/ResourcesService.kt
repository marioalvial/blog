package br.com.ms.blog.services

import br.com.ms.blog.utils.annotations.PageableMethod
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Service
class ResourcesService(
        private val requestMappingHandlerMapping: RequestMappingHandlerMapping
) {

    fun getResourcesInfo()= requestMappingHandlerMapping.handlerMethods
            .filter { isFindAllMethod(it.key, it.value) }
            .keys

    private fun isFindAllMethod(info: RequestMappingInfo, method: HandlerMethod) = info.paramsCondition.isEmpty
            && info.methodsCondition.methods.none { it != GET }
            && method.hasMethodAnnotation(PageableMethod::class.java)
            && method.methodParameters.size == 1
            && method.toString().contains("findAll(")
}