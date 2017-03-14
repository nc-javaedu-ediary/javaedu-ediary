package com.ncjavaedu.ediary.liferayportlet.stub;

/**
 * Created by abogdanov on 14.03.17.
 */

import com.liferay.portal.kernel.util.ReleaseInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

    @RenderMapping
    public String question(Model model) {
        model.addAttribute("message", "This is stub");

        return "liferayportlet/view";
    }

}
