package com.zhanghui.appface.mvc.resolver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.zhanghui.appface.common.StringUtils;
import com.zhanghui.appface.mvc.annotation.Ids;

public class IdsResolver extends AbstractNamedValueMethodArgumentResolver {

	public IdsResolver() {
		super(null);
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Ids.class);
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		Ids annotation = parameter
				.getParameterAnnotation(Ids.class);
		return new IdsNamedValueInfo(annotation);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest request) throws Exception {
		Map<String, String> uriTemplateVars = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		return (uriTemplateVars != null) ? StringUtils.splitInts(uriTemplateVars.get(name)) : null;
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter param)
			throws ServletRequestBindingException {
		String paramType = param.getParameterType().getName();
		throw new ServletRequestBindingException(
				"Missing URI template variable '" + name
						+ "' for method parameter type [" + paramType + "]");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void handleResolvedValue(Object arg, String name,
			MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest request) {
		String key = View.PATH_VARIABLES;
		int scope = RequestAttributes.SCOPE_REQUEST;
		Map<String, Object> pathVars = (Map<String, Object>) request
				.getAttribute(key, scope);
		if (pathVars == null) {
			pathVars = new HashMap<String, Object>();
			request.setAttribute(key, pathVars, scope);
		}
		pathVars.put(name, arg);
	}

	private static class IdsNamedValueInfo extends NamedValueInfo {

		private IdsNamedValueInfo(Ids annotation) {
			super(annotation.value(), true, ValueConstants.DEFAULT_NONE);
		}
	}
}
