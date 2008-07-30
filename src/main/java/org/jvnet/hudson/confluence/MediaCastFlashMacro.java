package org.jvnet.hudson.confluence;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;

import java.util.Map;

/**
 * Puts the link to Mediacast resource.
 *
 * Usage:
 *
 * {mediacast-flash:width=100|height=50|url=http://mediacast.sun.com/users/RamaPulavarthi/media/Hudson-Intro.swf}
 */
public class MediaCastFlashMacro extends BaseMacro {
    public boolean isInline() {
        return false;
    }

    public boolean hasBody() {
        return false;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.NO_RENDER;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {

        Integer id = (Integer) renderContext.getParam("mediacast-id");
        if(id==null)    id = Integer.valueOf(0);
        else            id = Integer.valueOf(id.intValue()+1);
        renderContext.getParams().put("mediacast-id",id);

        Map context = MacroUtils.defaultVelocityContext();
        context.put("id", id);
        context.put("url", params.get("url"));
        context.put("style", params.get("style"));
        context.put("width", defaulted(params, "width", "470"));
        context.put("height", defaulted(params, "height", "350"));
        return VelocityUtils.getRenderedTemplate("org/jvnet/hudson/confluence/mediacast-flash.vm", context);
    }

    private String defaulted(Map params, String name, String defaultValue) {
        String width = (String)params.get(name);
        if(width==null) width = defaultValue;
        return width;
    }
}