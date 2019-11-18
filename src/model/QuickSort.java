package model;

/**
 * @author Jesus Felipe
 */
public class QuickSort {
   
        private int contadorLinhas = 0;
        public int getContadorLinhas(){
            return this.contadorLinhas;
        }

    
    public int separar(Integer vetor[], int esquerda, int direita){
        
        contadorLinhas+=2;
        
        Integer pivo = vetor[direita];                                              // Pivo será o último elemento do vetor.  
        int i = (esquerda-1);                                                   // index do menor elemento -1 (out of range)
        contadorLinhas+=direita + 1;
        
        for (int j = esquerda; j < direita; j++) {                              //Nessa instrução verificará se o elemento é menor ou igual ao pivo
            
            contadorLinhas++;
            if (vetor[j] <= pivo) {                                             //Caso a condicional seja verdadeira, troca o vetor "i" e "j".
                i++;
               
                Integer troca = vetor[i];                                            //Executa as trocas para fazer a ordenação
                vetor[i] = vetor[j]; 
                vetor[j] = troca;
                contadorLinhas += 4;
            }
        }
        
        contadorLinhas+=4;
        Integer troca = vetor[i+1];                                                  //Executa trocas
        vetor[i+1] = vetor[direita]; 
        vetor[direita] = troca;
        return i+1; 
    } 
    
 /*IMPLEMENTACAO DO QUICK SORT*/
    public void quickSort(Integer vetor[], int esquerda, int direita) {
        contadorLinhas++;
        if (esquerda < direita) { 
            Integer posicaoPivo = separar(vetor, esquerda, direita); 
            quickSort(vetor, esquerda, posicaoPivo-1); 
            quickSort(vetor, posicaoPivo+1, direita);
            contadorLinhas += 3;
        } 
    }     
}
