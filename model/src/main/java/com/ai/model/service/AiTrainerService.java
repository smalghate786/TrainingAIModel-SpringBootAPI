package com.ai.model.service;



import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Service;

import com.ai.model.dto.TrainingRequest;

@Service
public class AiTrainerService {

    private MultiLayerNetwork model;
    private TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    private List<String> vocab = new ArrayList<>();
    private List<TrainingRequest> trainingData = new ArrayList<>();

    public String addTrainingData(TrainingRequest req) {
        trainingData.add(req);
        for (String token : tokenizerFactory.create(req.getText().toLowerCase()).getTokens()) {
            if (!vocab.contains(token)) vocab.add(token);
        }
        return "Training data added.";
    }

    public String trainModel() {
        if (trainingData.isEmpty()) {
            return "No training data available.";
        }

        int inputSize = vocab.size();
        int outputSize = 2; // negative / positive

        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(123)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputSize).nOut(8).activation(Activation.RELU).build())
                .layer(1, new OutputLayer.Builder()
                        .nIn(8)
                        .nOut(outputSize)
                        .activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MCXENT)
                        .build())
                .build();

        model = new MultiLayerNetwork(config);
        model.init();

        // Prepare dataset
        INDArray features = Nd4j.zeros(trainingData.size(), inputSize);
        INDArray labels = Nd4j.zeros(trainingData.size(), outputSize);

        for (int i = 0; i < trainingData.size(); i++) {
            TrainingRequest req = trainingData.get(i);
            String[] tokens = tokenizerFactory.create(req.getText().toLowerCase()).getTokens().toArray(new String[0]);
            for (String token : tokens) {
                int idx = vocab.indexOf(token);
                if (idx >= 0) features.putScalar(new int[]{i, idx}, 1.0);
            }
            labels.putScalar(new int[]{i, req.getLabel()}, 1.0);
        }

        DataSet ds = new DataSet(features, labels);
        for (int epoch = 0; epoch < 500; epoch++) {
            model.fit(ds);
        }

        return "Model trained successfully with " + trainingData.size() + " samples.";
    }

    public String predict(String text) {
        if (model == null) return "Model not trained yet.";

        INDArray features = Nd4j.zeros(1, vocab.size());
        String[] tokens = tokenizerFactory.create(text.toLowerCase()).getTokens().toArray(new String[0]);
        for (String token : tokens) {
            int idx = vocab.indexOf(token);
            if (idx >= 0) features.putScalar(new int[]{0, idx}, 1.0);
        }

        INDArray output = model.output(features);
        int classIdx = Nd4j.argMax(output, 1).getInt(0);
        return classIdx == 1 ? "Positive" : "Negative";
    }
}

