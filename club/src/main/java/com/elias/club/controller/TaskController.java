package com.elias.club.controller;


import com.elias.club.ClubApplication;
import com.elias.club.model.Boleto;
import com.elias.club.model.Carteira;
import com.elias.club.model.Tipo;
import com.elias.club.model.Usuario;
import com.elias.club.model.dto.UsuarioCadastroDto;
import com.elias.club.model.dto.UsuarioCarteiraDTO;
import com.elias.club.model.dto.UsuarioLoginDTO;
import com.elias.club.model.repository.UsuarioRepository;
import com.elias.club.model.service.UsuarioService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;


@Controller
public class TaskController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    //views
    @GetMapping("/cadastro")
    public String cadastro(){
        return "cadastro";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/informativo")
    public String informativo(){
        return "informativo";
    }
    @GetMapping("/sistemaAdm")
    public String adm(){
        if (ClubApplication.getUsuario().getTipo().equals(Tipo.ADMINISTRADOR)){
            return "sistemaAdm";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/sistemaUsuario")
    public String user(){
        return "sistemaUsuario";
    }

    @GetMapping("/liberarCarteiras")
    public String liberarCarteiras(){
        return "liberarCarteiras";
    }
    @GetMapping("/enviarNoticias")
    public String enviarNoticias(){
        return "enviarNoticias";
    }
    @GetMapping("/verificarStatus")
    public String verificarStatus(){
        return "verificarStatus";
    }
    @GetMapping("/solicitarCarteira")
    public String solicitarCarteira(ModelMap model){
        model.addAttribute("nome", ClubApplication.getUsuario().getName());
        model.addAttribute("dataNascimento", ClubApplication.getUsuario().getDataNascimento());
        model.addAttribute("cpf", ClubApplication.getUsuario().getCpf());
        model.addAttribute("email", ClubApplication.getUsuario().getEmail());
        return "solicitarCarteira";
    }
    @GetMapping("/quadroInformacoes")
    public String quadroInformacoes(){
        return "quadroInformacoes";
    }
    @GetMapping("/emitirCarteira")
    public String emitirCarteira(ModelMap model){
        if (ClubApplication.getUsuario().getBoleto().getStatusPago()){
            Carteira carteira = new Carteira(null, LocalDate.now(), (LocalDate.now().plusYears(1)), null);
            ClubApplication.getUsuario().setCarteira(carteira);
            usuarioService.atualizar(ClubApplication.getUsuario());
            model.addAttribute("nome", ClubApplication.getUsuario().getName());
            model.addAttribute("dataNascimento", ClubApplication.getUsuario().getDataNascimento());
            model.addAttribute("email", ClubApplication.getUsuario().getEmail());
            model.addAttribute("dataAtivacao", ClubApplication.getUsuario().getCarteira().getDataAtivacao());
            model.addAttribute("dataValidade", ClubApplication.getUsuario().getCarteira().getDataValidade());
            return "emitirCarteira";
        }else {
            return "sistemaUsuario";
        }

    }

    @GetMapping("/emitirBoleto")
    public String emitirBoleto(ModelMap model) throws IOException, WriterException {
        model.addAttribute("nome", ClubApplication.getUsuario().getName());
        model.addAttribute("dataEmissao", ClubApplication.getUsuario().getBoleto().getDataEmissao());
        model.addAttribute("dataValidade", ClubApplication.getUsuario().getBoleto().getDataValidade());
        model.addAttribute("qrcode", ClubApplication.getUsuario().getBoleto().criarBoleto());
        return "emitirBoleto";
    }

    @PostMapping("/registrar")
    public String cadastrar(UsuarioCadastroDto usuarioCadastroDto){
        Usuario usuario = new Usuario(usuarioCadastroDto);
        if (usuarioService.findAll().isEmpty()){
            usuario.setTipo(Tipo.ADMINISTRADOR);
        }else{
            usuario.setTipo(Tipo.USUARIO);
        }
        usuarioService.cadastrar(usuario);

        return "redirect:/";
    }

    @GetMapping("/gerarBoleto")
    public String gerarBoleto() {
        Boleto boleto =new Boleto(null, 651111, LocalDate.now(), LocalDate.now(), false);
        ClubApplication.getUsuario().setBoleto(boleto);
        usuarioService.atualizar(ClubApplication.getUsuario());
        return "sistemaUsuario";
    }
    @PostMapping("/liberar")
    public String liberar(UsuarioCarteiraDTO usuarioCarteiraDTO) {
        Usuario usuarioLiberar = usuarioService.findByCpf(usuarioCarteiraDTO.cpf());

        if (usuarioLiberar != null && usuarioLiberar.getName().equals(usuarioCarteiraDTO.nome())) {
            usuarioLiberar.getBoleto().setStatusPago(true);
            usuarioService.atualizar(usuarioLiberar);
        }

        return "sistemaAdm";
    }
    @PostMapping("/suspender")
    public String suspender(UsuarioCarteiraDTO usuarioCarteiraDTO){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioService.findAll();
        Usuario usuarioSuspender = new Usuario();

        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(usuarioCarteiraDTO.cpf())){
                usuarioSuspender = usuario;
            }
        }
        if (usuarioSuspender.getName().equals(usuarioCarteiraDTO.nome())){
            usuarioSuspender.getBoleto().setStatusPago(false);
        }
        return "sistemaAdm";
    }










    @PostMapping("/login")
    public String login(UsuarioLoginDTO usuarioDTO){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioService.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(usuarioDTO.cpf())){
                ClubApplication.setUsuario(usuario);
            }
        }
        if (ClubApplication.getUsuario().getPassword().equals(usuarioDTO.password())){
            switch (ClubApplication.getUsuario().getTipo()){
                case USUARIO:
                    return "redirect:/sistemaUsuario";
                case ADMINISTRADOR:
                    return "redirect:/sistemaAdm";

            }
        }else {
            System.out.println("senha nao confere");
        }
        return "index";

    }

}
