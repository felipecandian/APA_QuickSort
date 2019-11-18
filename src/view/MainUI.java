package view;

import model.QuickSort;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Felipe Candian
 */
public class MainUI extends javax.swing.JFrame {

    /**
     * Creates new form MainUI
     */
    public MainUI() throws IOException {
        initComponents();
        File arquivo = new File("Vetor.txt");
        arquivo.createNewFile();
        this.carregarVetores("Vetor");     
    }
    
    //<editor-fold defaultstate="collapsed" desc="VARS">
    private ArrayList<Integer> ListaElemento = new ArrayList();
    private Integer[] AuxVet;
    
    //<editor-fold defaultstate="collapsed" desc="GET/SET/METHODS">
    
    
    //Funções de Acessos em arquivos.
    public void atualizarArquivo(){
        try {
            ListaElemento.clear();
            apagarElementos("VetorOrdenado");
            carregarVetores("Vetor");
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carregarVetores(String fileName) throws IOException{
        
        //<editor-fold defaultstate="collapsed" desc="Vars">
        String write = null;
        ArrayList<Integer> auxList = new ArrayList();
        //</editor-fold>
        try{
            //Lê do arquivo usando o delimitador "," e passa para o MainArray
            FileReader f = new FileReader(fileName + ".txt");
            Scanner lerArq = new Scanner(f);
            lerArq.useDelimiter(";");
            while(lerArq.hasNext()){
                write = lerArq.next();
                auxList.add(Integer.parseInt(write));
            }
            int AuxSize = auxList.size();
            for(int i = 0; i < AuxSize; i++){
                ListaElemento.add(auxList.get(i));
            }
            //set as labels
            lblTamanho.setText("Tamanho do vetor: " + AuxSize);
            lblArquivo.setText("Arquivo: " + fileName + ".txt");
            f.close();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
    }
    
    public void salvarVetorOrdenado(ArrayList<Integer> List, String fileName){
        try{
            String auxString = null;
            for(int i = 0; i < (List.size()) ; i++){
                if(auxString == null){
                    auxString = String.valueOf(List.get(i));
                }else
                    auxString += ";" + String.valueOf(List.get(i));
            }
            FileWriter arq = new FileWriter(fileName + ".txt");
            PrintWriter gravArq = new PrintWriter(arq);
            gravArq.print(auxString);
            arq.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro " + e, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    //Função para preenchimento randomico.
    public void preencherAleatorio() throws IOException{
        //Essa função substituira o array principal já criado.
        //E provavelmente vai deixar o programa mais pesadinho por carregar o arquivo de novo 2 vezes KKKKKKKKKKj 
        //limpa a lista e a preenche com 500 números
        ListaElemento.clear();
        Random gen = new Random();
        for(int i = 0; i < 5000; i++){
            //Preenche a posição i do MainArray com um número aleatório do intervalo [0, 1000]
            ListaElemento.add(gen.nextInt(11001));
        }
        //Acessa o arquivo mais duas fucking vezes :) desculpa memoria.
        salvarVetorOrdenado(ListaElemento, "Vetor");
        carregarVetores("Vetor");
    }
    
    //função de analise de dados
    public int maximoMinimo(Integer vetor[], String op){
        Integer max = vetor[0];
        Integer min = vetor[0];
        for(int i = 0; i < vetor.length; i++){
          if(vetor[i] >= max)
              max = vetor[i];
          
          if(vetor[i] <= min)
              min = vetor[i];
        }
        switch(op){
            case "max": 
                return max;
            case "min":
                return min;
            default:
                return 0;
        }
    }
    
    
    public void apagarElementos(String file){
        try{
            FileWriter arq = new FileWriter(file + ".txt");
            System.out.println("Cleaning file: " + file + ".txt");
            PrintWriter gravArq = new PrintWriter(arq);
            gravArq.print("");
            arq.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro :->" + e, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    
    public void piorCaso(){
        //Pra gerar um pior caso, o quick sort tem q basicamente pegar os pivos já na posição deles (como ultimo elemento).
        //ent um vetor já ordenado resultaria em um pior caso nesse algoritmo
        //já que ele pega o ultimo elemento como pivo, uma sequencia já ordenada sempre teria o pivo na posição certa.
        //Logo,
        Integer[] AuxVet2 = new Integer[500];
        for(int i = 0; i < 500; i++){
            AuxVet2[i] = i;
        }
        
        //Retorna o Vet para o array list
        ListaElemento.clear();
        for(int j = 0; j < AuxVet2.length; j++){
            ListaElemento.add(AuxVet2[j]);
        }
        //salva no arquivo
        salvarVetorOrdenado(ListaElemento, "Vetor");
    }
    
    
    public void melhorCaso(){
        //Pra gerar um melhor caso, o quick sort tem q executar a poha td normalmente mas com um pivo nice.
        //a random faz basicamente samerda aq, mas essa vai gerar com um pivo tentando estar no meio do coiso.
        Random gen = new Random();
        int pivo = gen.nextInt(1000);
        
        //tendo certeza de q o pivo n é um valor tão pequeno.
        pivo += 150;
        //Array aux
        Integer[] AuxVet2 = new Integer[500];
        
        AuxVet2[499] = pivo;
        
        //preenche metade do vetor com valores menores q o pivo
        for(int i = 0; i < 225; i++){
            AuxVet2[i] = gen.nextInt(pivo);
        }
        //preenche a outra metade com valores maiores q o pivo
        for(int i = 225; i < 499; i++){
            AuxVet2[i] = gen.nextInt(800) + pivo;
        }
        
        //Retorna o Vet para o array list
        ListaElemento.clear();
        for(int j = 0; j < AuxVet2.length; j++){
            ListaElemento.add(AuxVet2[j]);
        }
        //salva no arquivo
        salvarVetorOrdenado(ListaElemento, "Vetor");
    }
    
    
  
    
    
    //</editor-fold>
    //</editor-fold>
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        painelInformacao = new javax.swing.JPanel();
        lblTamanho = new javax.swing.JLabel();
        lblArquivo = new javax.swing.JLabel();
        lblContadorLinhas = new javax.swing.JLabel();
        lblComplexidade = new javax.swing.JLabel();
        lblTempoExecucao = new javax.swing.JLabel();
        painelFuncao = new javax.swing.JPanel();
        lblVetorAleatorio = new javax.swing.JLabel();
        btnGerarAleatorio = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        lblOrdenar = new javax.swing.JLabel();
        lblGerarMelhor = new javax.swing.JLabel();
        btnGerarMelhor = new javax.swing.JButton();
        lblGerarPior = new javax.swing.JLabel();
        btnGerarPior = new javax.swing.JButton();
        lblApagar = new javax.swing.JLabel();
        btnApagar = new javax.swing.JButton();
        lblAtualizar = new javax.swing.JLabel();
        btnAtualizar = new javax.swing.JButton();
        lblVetorOrdenado = new javax.swing.JLabel();
        lblVetorDesordenado = new javax.swing.JLabel();
        btnVisualisarOrdenado = new javax.swing.JButton();
        btnVisualisarDesordenado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        painel.setBackground(new java.awt.Color(204, 255, 204));

        lblTitulo.setFont(new java.awt.Font("GOOD BRUSH", 0, 36)); // NOI18N
        lblTitulo.setText("Quick Sort ");

        painelInformacao.setBackground(new java.awt.Color(255, 255, 255));

        lblTamanho.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTamanho.setText("Tamanho do vetor:");
        lblTamanho.setToolTipText("Tamanho do array.\n");

        lblArquivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblArquivo.setText("Arquivo:");
        lblArquivo.setToolTipText("Arquivo do array.");

        lblContadorLinhas.setText("Linhas Executadas:");
        lblContadorLinhas.setToolTipText("Linhas executadas do algoritmo.");

        lblComplexidade.setText("Complexidade: ");
        lblComplexidade.setToolTipText("Complexidade do algoritmo.");

        lblTempoExecucao.setText("Tempo de Execução: ");
        lblTempoExecucao.setToolTipText("Tempo de execução do algoritmo.");

        javax.swing.GroupLayout painelInformacaoLayout = new javax.swing.GroupLayout(painelInformacao);
        painelInformacao.setLayout(painelInformacaoLayout);
        painelInformacaoLayout.setHorizontalGroup(
            painelInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComplexidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelInformacaoLayout.createSequentialGroup()
                        .addComponent(lblContadorLinhas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(painelInformacaoLayout.createSequentialGroup()
                        .addComponent(lblTempoExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(painelInformacaoLayout.createSequentialGroup()
                        .addComponent(lblArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        painelInformacaoLayout.setVerticalGroup(
            painelInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelInformacaoLayout.createSequentialGroup()
                .addGroup(painelInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTamanho)
                    .addComponent(lblArquivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblContadorLinhas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTempoExecucao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblComplexidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelFuncao.setBackground(new java.awt.Color(255, 255, 255));
        painelFuncao.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblVetorAleatorio.setText("Vetor Aleatorio:");
        lblVetorAleatorio.setToolTipText("Preenchimento aleatório.\n");

        btnGerarAleatorio.setText("Gerar");
        btnGerarAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarAleatorioActionPerformed(evt);
            }
        });

        btnOrdenar.setText("Ordenar QuickSort");
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        lblOrdenar.setText("Ordenar Vetor:");
        lblOrdenar.setToolTipText("Executar o QuickSort.");

        lblGerarMelhor.setText("Gerar Melhor Caso:");

        btnGerarMelhor.setText("Gerar");
        btnGerarMelhor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarMelhorActionPerformed(evt);
            }
        });

        lblGerarPior.setText("Gerar Pior Caso:");

        btnGerarPior.setText("Gerar");
        btnGerarPior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarPiorActionPerformed(evt);
            }
        });

        lblApagar.setText("Apagar Vetor:");

        btnApagar.setText("Apagar");
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        lblAtualizar.setText("Atualizar Arquivo TXT:");
        lblAtualizar.setToolTipText("Recarregar o arquivo.");

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelFuncaoLayout = new javax.swing.GroupLayout(painelFuncao);
        painelFuncao.setLayout(painelFuncaoLayout);
        painelFuncaoLayout.setHorizontalGroup(
            painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFuncaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelFuncaoLayout.createSequentialGroup()
                            .addComponent(lblGerarMelhor)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnGerarMelhor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelFuncaoLayout.createSequentialGroup()
                            .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblGerarPior, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblApagar))
                            .addGap(18, 18, 18)
                            .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnGerarPior, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(painelFuncaoLayout.createSequentialGroup()
                        .addComponent(lblVetorAleatorio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGerarAleatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFuncaoLayout.createSequentialGroup()
                        .addComponent(lblAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar))
                    .addGroup(painelFuncaoLayout.createSequentialGroup()
                        .addComponent(lblOrdenar)
                        .addGap(13, 13, 13)
                        .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelFuncaoLayout.setVerticalGroup(
            painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFuncaoLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOrdenar)
                    .addComponent(btnOrdenar)
                    .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblVetorAleatorio)
                        .addComponent(btnGerarAleatorio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFuncaoLayout.createSequentialGroup()
                        .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGerarMelhor)
                            .addComponent(btnGerarMelhor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGerarPior)
                            .addComponent(btnGerarPior)))
                    .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAtualizar)
                        .addComponent(lblAtualizar)))
                .addGap(18, 18, 18)
                .addGroup(painelFuncaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApagar)
                    .addComponent(btnApagar))
                .addGap(20, 20, 20))
        );

        lblVetorOrdenado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVetorOrdenado.setText("Vetor Ordenado:");
        lblVetorOrdenado.setToolTipText("Array organizado.");

        lblVetorDesordenado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVetorDesordenado.setText("Vetor Desordenado:");

        btnVisualisarOrdenado.setText("Abrir");
        btnVisualisarOrdenado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualisarOrdenadoActionPerformed(evt);
            }
        });

        btnVisualisarDesordenado.setText("Abrir");
        btnVisualisarDesordenado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualisarDesordenadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(painelInformacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelFuncao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(painelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblVetorDesordenado)
                .addGap(23, 23, 23)
                .addComponent(btnVisualisarDesordenado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVetorOrdenado, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVisualisarOrdenado)
                .addGap(47, 47, 47))
            .addGroup(painelLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelLayout.setVerticalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(painelFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVisualisarDesordenado)
                        .addComponent(lblVetorOrdenado)
                        .addComponent(btnVisualisarOrdenado))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addComponent(lblVetorDesordenado)
                        .addGap(5, 5, 5)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVisualisarDesordenadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualisarDesordenadoActionPerformed
        try{
            JOptionPane.showMessageDialog(null, "Caso deseje alterar o array\nUse \",\" sem espaços\npara separar os elementos", "Info", JOptionPane.INFORMATION_MESSAGE);
            File fl = new File("Vetor.txt");
            fl.createNewFile();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(fl);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro :" + e, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }//GEN-LAST:event_btnVisualisarDesordenadoActionPerformed

    private void btnVisualisarOrdenadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualisarOrdenadoActionPerformed
        try{
            JOptionPane.showMessageDialog(null, "Caso deseje alterar o array\nUse \",\" sem espaços\npara separar os elementos", "Info", JOptionPane.INFORMATION_MESSAGE);
            File fl = new File("VetorOrdenado.txt");
            fl.createNewFile();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(fl);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro :" + e, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }//GEN-LAST:event_btnVisualisarOrdenadoActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        // Limpa o arquivo do array ordenado.
        apagarElementos("VetorOrdenado");
        
        // Cria uma instância da classe QuickSort.
        QuickSort Sort = new QuickSort();
        
        // Copia a lista para o vetor
        AuxVet = new Integer[ListaElemento.size()];
        AuxVet = ListaElemento.toArray(AuxVet);
        
        // Testa a complexidade complexidade (Testa somente com a primeira execução do quicksort...)
        int max = maximoMinimo(AuxVet, "max"), min = maximoMinimo(AuxVet, "Min"), maxI = max, minI = min;
        for(int y = 0; y < 10; y ++){
            if((AuxVet[AuxVet.length - 1] == max) || (AuxVet[AuxVet.length - 1] == max) ||(AuxVet[AuxVet.length - 1] == maxI)||(AuxVet[AuxVet.length - 1] == minI)){
            lblComplexidade.setText("Complexity: O(n²)");
            break;
            }else{
                lblComplexidade.setText("Complexity: O(n*log(n))");
            }
            //altera os valores de comparação se o pivo é o menor ou o maior elemento do vetor e em um intervalo para checar a complexidade. 
            max++;
            min++;
            maxI--;
            minI--;
        }
        
        // Pega o tempo inicial da execução.
        long startingTime = System.currentTimeMillis();
        
        // Chama o metodo do quicksort passando o array (no vetor aux, com o menor elemento em 0 e o pivo como a última posição do vetor)
        System.out.println("Sorting");
        Sort.quickSort(AuxVet, 0, AuxVet.length - 1);
        
        
        //altera as labels e da avisos
        
        lblTempoExecucao.setText("Tempo de Execução: " + (float)(System.currentTimeMillis() - startingTime) + "ms");
        lblContadorLinhas.setText(String.valueOf("Linhas Executadas: " + Sort.getContadorLinhas()));
        
        //Retorna o Vet para o array list
        ListaElemento.clear();
        for(int j = 0; j < AuxVet.length; j++){
            ListaElemento.add(AuxVet[j]);
        }
        salvarVetorOrdenado(ListaElemento, "VetorOrdenado");
        JOptionPane.showMessageDialog(null, "O array foi reorganizado", "Info", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnGerarAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarAleatorioActionPerformed
        try {
            int y = JOptionPane.showConfirmDialog(null, "O array sera preenchido com números aleatórios, deseja continuar?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(y == 0){
                preencherAleatorio();
                atualizarArquivo();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGerarAleatorioActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        //somente limpa a lista e a recarrega do arquivo.
        atualizarArquivo();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnGerarPiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarPiorActionPerformed
        // O array sera preenchido com um vetor já ordenado
        // onde cada pivo das partições estará na sua devida posição.
        int y = JOptionPane.showConfirmDialog(null, "O array sera preenchido com números aleatórios de forma \nque a complexidade seja do pior caso\ndeseja continuar?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(y == 0){
            piorCaso();
            atualizarArquivo();
        }
    }//GEN-LAST:event_btnGerarPiorActionPerformed

    private void btnGerarMelhorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarMelhorActionPerformed
        // O array sera preenchido de certa forma que o pivo seja colocado +/- como um valor médio.
        int y = JOptionPane.showConfirmDialog(null, "O array sera preenchido com números aleatórios de forma \nque a complexidade seja do melhor caso (não garantido)\ndeseja continuar?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(y == 0){
            melhorCaso();
            atualizarArquivo();
        }       
    }//GEN-LAST:event_btnGerarMelhorActionPerformed

    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        apagarElementos("Vetor");
        atualizarArquivo();
    }//GEN-LAST:event_btnApagarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //Add the code below to put a better look to the screen C: <3
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainUI().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnGerarAleatorio;
    private javax.swing.JButton btnGerarMelhor;
    private javax.swing.JButton btnGerarPior;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnVisualisarDesordenado;
    private javax.swing.JButton btnVisualisarOrdenado;
    private javax.swing.JLabel lblApagar;
    private javax.swing.JLabel lblArquivo;
    private javax.swing.JLabel lblAtualizar;
    private javax.swing.JLabel lblComplexidade;
    private javax.swing.JLabel lblContadorLinhas;
    private javax.swing.JLabel lblGerarMelhor;
    private javax.swing.JLabel lblGerarPior;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblTamanho;
    private javax.swing.JLabel lblTempoExecucao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVetorAleatorio;
    private javax.swing.JLabel lblVetorDesordenado;
    private javax.swing.JLabel lblVetorOrdenado;
    private javax.swing.JPanel painel;
    private javax.swing.JPanel painelFuncao;
    private javax.swing.JPanel painelInformacao;
    // End of variables declaration//GEN-END:variables
}
