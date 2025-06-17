/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maria
 */
public class Cliente {
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;

    public Cliente(String nome, String sobrenome, String rg, String cpf, String endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    
    public String getNome() { return nome; }
    public String getSobrenome() { return sobrenome; }
    public String getRg() { return rg; }
    public String getCpf() { return cpf; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public void setRg(String rg) { this.rg = rg; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
