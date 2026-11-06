package com.lookation.view

import com.lookation.global.error.ApiException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.ModelAndView

/**
 * HTML 컨트롤러 전용 예외 처리.
 *
 * `ApiExceptionHandler`는 `com.lookation.controller`만 처리하므로 HTML 예외가 ProblemDetail로
 * 새지 않는다. 이 어드바이스는 그 반대로 HTML 컨트롤러 예외를 `templates/error.html`로 렌더한다.
 */
@ControllerAdvice(basePackages = ["com.lookation.view"])
class HtmlExceptionHandler(
    private val viewerLookup: ViewerLookup,
) {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException, authentication: Authentication?): ModelAndView =
        errorView(exception.status, exception.message, authentication)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(exception: AccessDeniedException, authentication: Authentication?): ModelAndView =
        errorView(HttpStatus.FORBIDDEN, "이 화면에 접근할 권한이 없습니다", authentication)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class, MissingServletRequestParameterException::class)
    fun handleBadRequest(exception: Exception, authentication: Authentication?): ModelAndView =
        errorView(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다", authentication)

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(exception: Exception, authentication: Authentication?): ModelAndView {
        logger.error("Unhandled HTML exception", exception)
        return errorView(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다", authentication)
    }

    private fun errorView(
        status: HttpStatus,
        message: String,
        authentication: Authentication?,
    ): ModelAndView =
        ModelAndView("error").apply {
            this.status = status
            addObject("status", status.value())
            addObject("errorTitle", title(status))
            addObject("message", message)
            addObject("viewer", viewerLookup.resolve(authentication))
        }

    private fun title(status: HttpStatus): String =
        when (status) {
            HttpStatus.BAD_REQUEST -> "요청을 처리할 수 없습니다"
            HttpStatus.UNAUTHORIZED -> "로그인이 필요합니다"
            HttpStatus.FORBIDDEN -> "접근 권한이 없습니다"
            HttpStatus.NOT_FOUND -> "페이지를 찾을 수 없습니다"
            HttpStatus.CONFLICT -> "지금은 처리할 수 없습니다"
            HttpStatus.TOO_MANY_REQUESTS -> "잠시 후 다시 시도해 주세요"
            else -> "문제가 발생했습니다"
        }

    private companion object {
        val logger = LoggerFactory.getLogger(HtmlExceptionHandler::class.java)
    }
}
