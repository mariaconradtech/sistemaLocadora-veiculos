package modelo;


public class Cliente {
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;
    private boolean temVeiculoLocado; // ADICIONADO dentro da classe

    public Cliente(String nome, String sobrenome, String rg, String cpf, String endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
        this.temVeiculoLocado = false; // valor padrão
    }

    // Getters e Setters
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

    // Getters e Setters do temVeiculoLocado
    public boolean isTemVeiculoLocado() {
        return temVeiculoLocado;
    }

    public void setTemVeiculoLocado(boolean temVeiculoLocado) {
        this.temVeiculoLocado = temVeiculoLocado;
    }
}

