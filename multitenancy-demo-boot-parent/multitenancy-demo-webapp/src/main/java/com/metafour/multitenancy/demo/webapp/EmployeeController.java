package com.metafour.multitenancy.demo.webapp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.metafour.multitenancy.demo.webapp.bean.Employee;
import com.metafour.multitenancy.demo.webapp.bean.EmployeeRepository;

/**
 * Spring controller to handle employee list, add calls.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 */
@Controller
@RequestMapping("/{tenantid}")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping
	// public String employees(@RequestHeader("X-TenantID") String tenantid, Model model) {
	public String employees(@PathVariable String tenantid, Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employees", employeeRepository.findAll());
		return "employees";
	}

	@RequestMapping(value = "/get/{empid}", method = RequestMethod.GET)
	public String getEmployee(@PathVariable String tenantid, @PathVariable Long empid, Model model) {
		model.addAttribute("employee", employeeRepository.findById(empid).orElse(null));
		model.addAttribute("employees", employeeRepository.findAll());
		return "employees";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Transactional
	// public String addEmployee(@RequestHeader("X-TenantID") String tenantid, @ModelAttribute Employee employee, Model model) {
	public String addEmployee(@PathVariable String tenantid, @Valid @ModelAttribute Employee employee, BindingResult result) {
		if (result.hasErrors()) return "error";
		employeeRepository.save(employee);
		return "redirect:/{tenantid}";
	}
}
