package com.lookation.view

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

/**
 * HTML 화면에 공통으로 넣는 로그인 사용자 정보다.
 *
 * API 컨트롤러에는 적용하지 않는다(`basePackages = com.lookation.view`). API 체인의 모델은 비어 있어야 한다.
 */
@ControllerAdvice(basePackages = ["com.lookation.view"])
class ViewAdvice(
    private val viewerLookup: ViewerLookup,
) {

    @ModelAttribute("viewer")
    fun viewer(authentication: Authentication?): Viewer? = viewerLookup.resolve(authentication)
}
