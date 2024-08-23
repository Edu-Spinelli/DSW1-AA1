package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.EmpresaService;
import br.ufscar.dc.dsw.service.ProfissionalService;
import br.ufscar.dc.dsw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home() {
        return "admin/home"; // Página inicial do admin
    }

    // Rotas para gestão de empresas
    @GetMapping("/empresas")
    public String listarEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "admin/listarEmpresas";
    }

    @GetMapping("/empresas/cadastrar")
    public String cadastrarEmpresa(Empresa empresa) {
        return "admin/cadastrarEmpresa";
    }

    @PostMapping("/empresas/salvar")
    public String salvarEmpresa(@Valid Empresa empresa, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/cadastrarEmpresa";
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(empresa.getEmail());
        usuario.setSenha(passwordEncoder.encode(empresa.getSenha()));
        usuario.setRole("EMPRESA");
        usuarioService.salvar(usuario);

        empresaService.salvar(empresa);
        return "redirect:/admin/empresas";
    }

    @GetMapping("/empresas/editar/{id}")
    public String editarEmpresa(@PathVariable("id") Long id, Model model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "admin/editarEmpresa";
    }

    @PostMapping("/empresas/editar/{id}")
    public String atualizarEmpresa(@PathVariable("id") Long id, @Valid Empresa empresa, BindingResult result) {
        if (result.hasErrors()) {
            empresa.setId(id);
            return "admin/editarEmpresa";
        }
        empresaService.salvar(empresa);
        return "redirect:/admin/empresas";
    }

    @GetMapping("/empresas/deletar/{id}")
    public String deletarEmpresa(@PathVariable("id") Long id) {
        empresaService.deletar(id);
        return "redirect:/admin/empresas";
    }

    // Rotas para gestão de profissionais
    @GetMapping("/profissionais")
    public String listarProfissionais(Model model) {
        model.addAttribute("profissionais", profissionalService.buscarTodos());
        return "admin/listarProfissionais";
    }

    @GetMapping("/profissionais/cadastrar")
    public String cadastrarProfissional(Profissional profissional) {
        return "admin/cadastrarProfissional";
    }

    @PostMapping("/profissionais/salvar")
    public String salvarProfissional(@Valid Profissional profissional, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/cadastrarProfissional";
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(profissional.getEmail());
        usuario.setSenha(passwordEncoder.encode(profissional.getSenha()));
        usuario.setRole("PROFISSIONAL");
        usuarioService.salvar(usuario);

        profissionalService.salvar(profissional);
        return "redirect:/admin/profissionais";
    }

    @GetMapping("/profissionais/editar/{id}")
    public String editarProfissional(@PathVariable("id") Long id, Model model) {
        model.addAttribute("profissional", profissionalService.buscarPorId(id));
        return "admin/editarProfissional";
    }

    @PostMapping("/profissionais/editar/{id}")
    public String atualizarProfissional(@PathVariable("id") Long id, @Valid Profissional profissional, BindingResult result) {
        if (result.hasErrors()) {
            profissional.setId(id);
            return "admin/editarProfissional";
        }
        profissionalService.salvar(profissional);
        return "redirect:/admin/profissionais";
    }

    @GetMapping("/profissionais/deletar/{id}")
    public String deletarProfissional(@PathVariable("id") Long id) {
        profissionalService.deletar(id);
        return "redirect:/admin/profissionais";
    }
}
