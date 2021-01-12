package es.enxenio.sife1701.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jlosa on 25/08/2017.
 */
@Controller
@RequestMapping("/admin")
public class PrincipalAdminController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getLoginPage() {
        return "redirect:/#/login";
    }

}
