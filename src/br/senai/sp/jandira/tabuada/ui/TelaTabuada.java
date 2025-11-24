package br.senai.sp.jandira.tabuada.ui;

import br.senai.sp.jandira.tabuada.Tabuada;
import br.senai.sp.jandira.tabuada.model.TabuadaApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Optional;

public class TelaTabuada extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        // definir tamanho da tela
        //stage.setHeight(500);
        //stage.setWidth(500);
        stage.setResizable(false);
        stage.setTitle("Tabuada");

        // Criar root - componente principal
        VBox root = new VBox();

        //criamos a cena e adicionamos o root nela
        Scene scene = new Scene(root);
        root.setStyle("-fx-background-color: #8700ff;");

        //colocamos a cena no palco
        stage.setScene(scene);

        // criar header da tela
        VBox header = new VBox();
        //header.setPrefHeight(100);
        header.setStyle("-fx-background-color: #377a8e");

        //Criar label do header
        Label labelTitulo = new Label("Tabuada");
        labelTitulo.setStyle("-fx-text-fill: white;-fx-font-size: 30px;-fx-font-weight: bold;");
        labelTitulo.setPadding(new Insets(16, 8,0,8));

        Label labelSubtitulo = new Label("Crie a tabuada que sua imaginação mandar. ");
        labelSubtitulo.setPadding(new Insets(0,8,8,8));
        labelSubtitulo.setStyle("-fx-text-fill: white;");

        // Adicionar label no header
        header.getChildren().addAll(labelTitulo, labelSubtitulo);

        // Criar o grid de formulario
        GridPane gridFormulario = new GridPane();
        //gridFormulario.setPrefHeight(100);
        //gridFormulario.setStyle("-fx-background-color: #15ffc3;");
        gridFormulario.setVgap(10);
        gridFormulario.setHgap(10);
        gridFormulario.setPadding(new Insets(16,8,16,8));

        // Criar conteudo do grid
        Label labelMultiplicando = new Label("Multiplicando");
        TextField textFieldMultiplicando = new TextField();
        Label labelMenorMultiplicador = new Label("Menor Multiplicador");
        TextField textFieldMenorMultiplicador = new TextField();
        Label labelMaiorMultiplicador = new Label("Menor Multiplicador");
        TextField textFieldMaiorMultiplicador = new TextField();

        //Colocar componentes no grid
        gridFormulario.add(labelMultiplicando, 0, 0);
        gridFormulario.add(textFieldMultiplicando, 1, 0);
        gridFormulario.add(labelMenorMultiplicador, 0, 1);
        gridFormulario.add(textFieldMenorMultiplicador, 1, 1);
        gridFormulario.add(labelMaiorMultiplicador, 0, 2);
        gridFormulario.add(textFieldMaiorMultiplicador, 1, 2);

        //Criar a caixa de botões
        Pane paneButtons = new Pane();
        paneButtons.setPadding(new Insets(16,0,0,8));
        HBox boxBotao = new HBox();
        boxBotao.setPadding( new Insets(8));
        boxBotao.setSpacing(10);
        paneButtons.getChildren().add(boxBotao);

        //boxBotao.setPrefHeight(100);
        //boxBotao.setStyle("-fx-background-color: #00ff2a;");

        // Criar componentes
        Button buttonCalcular = new Button("Calcular");
        Button buttonLimpar = new Button("Limpar");
        Button buttonSair = new Button("Sair");

        // Adicionar dentro do Hbox
        boxBotao.getChildren().addAll(buttonCalcular, buttonLimpar, buttonSair);

        // Adicionar componentes a caixa

        //Criar caixa de resultados

        VBox boxResultados = new VBox();
        boxResultados.setPrefHeight(300);
        //boxResultados.setStyle("-fx-background-color: #ef00ff;");

        // criar componentes boxResultados
        Label labelResultados = new Label("Resultados");
        labelResultados.setPadding( new Insets(0,8,8,8));
        labelResultados.setStyle("-fx-text-fill: blue; -fx-font-size: 18px");
        ListView listaTabuada = new ListView();
        listaTabuada.setPadding( new Insets(8));
        listaTabuada.setPrefHeight(300);

        // Adicionar os componentes na boxResultados
        boxResultados.getChildren().addAll(labelResultados, listaTabuada);


        // adicionar componentes ao root
        //header filho do root
        root.getChildren().add(header);
        root.getChildren().add(gridFormulario);
        root.getChildren().add(paneButtons);
        root.getChildren().add(boxResultados);


        stage.show();

        buttonCalcular.setOnAction(e -> {
            TabuadaApp tabuada = new TabuadaApp();
            int multiplicando = Integer.parseInt(textFieldMultiplicando.getText());
            tabuada.multiplicando = multiplicando;

            tabuada.multiplicadorInicial =
                    Integer.parseInt( textFieldMenorMultiplicador.getText());

            tabuada.multiplicadorFinal =
                    Integer.parseInt( textFieldMaiorMultiplicador.getText());

            String[] resultado = tabuada.calcularTabuada();
            listaTabuada.getItems().addAll(resultado);

            //gravar dados da tabuada em arquivo
            Path arquivo = Path.of("c:\\Users\\25203660\\DS1T\\tabuada\\dados_tabuada.csv");

            String dados = textFieldMultiplicando.getText() +";"+textFieldMenorMultiplicador.getText()+";"+textFieldMaiorMultiplicador.getText()+";" + LocalDateTime.now() + "\n";

            try {
                Files.writeString(arquivo,dados, StandardOpenOption.APPEND);
            } catch (IOException erro) {
                System.out.println(erro.getMessage());
            }
        });
        buttonLimpar.setOnAction(e -> {
           textFieldMaiorMultiplicador.clear();
           textFieldMenorMultiplicador.clear();
           textFieldMultiplicando.clear();
           listaTabuada.getItems().clear();
           textFieldMultiplicando.requestFocus();

        });
        buttonSair.setOnAction(e -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,"Deseja finalizar o programa?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.get() == ButtonType.YES) {
                    stage.close();
                }
        });
    }
}
