package com.skopik.confluence.plugins.productivity.servlet;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 *
 */
public class PluginSettingsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PluginSettingsServlet.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private LoginUriProvider loginUriProvider;

    @Autowired
    private TemplateRenderer renderer;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserProfile user = userManager.getRemoteUser(request);
        if (user == null) {
            redirectToLogin(request, response);
            return;
        }

        Map<String, Object> context = MacroUtils.defaultVelocityContext();

        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        renderer.render("/template/admin/settings.vm", context, response.getWriter());
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(HttpServletRequest request) {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }

        return URI.create(builder.toString());
    }

}
