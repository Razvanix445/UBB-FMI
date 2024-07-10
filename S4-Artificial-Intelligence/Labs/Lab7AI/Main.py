import ANN_Classification

import warnings
from sklearn.exceptions import ConvergenceWarning

warnings.filterwarnings("ignore", category=ConvergenceWarning)

if __name__ == '__main__':
    print("Hello World!")

    print("\nANN Tool")
    real_images_paths = "normal_anime_data"
    sepia_images_paths = "sepia_anime_data"
    # TOOL
    # Accuracy: 0.8125
    # Precision: 0.8174603174603174
    # Recall: 0.8125
    # F1 Score: 0.8117647058823529

    # MANUAL
    # Accuracy: 0.78125
    # Precision: 0.7823529411764706
    # Recall: 0.78125
    # F1 Score: 0.7810361681329423

    # real_images_paths = ["normal_anime_data", "normal_animal_data/Bear", "normal_animal_data/Bird", "normal_animal_data/Cat", "normal_animal_data/Cow", "normal_animal_data/Deer", "normal_animal_data/Dog",
    #                      "normal_animal_data/Dolphin", "normal_animal_data/Elephant", "normal_animal_data/Giraffe", "normal_animal_data/Horse", "normal_animal_data/Kangaroo",
    #                      "normal_animal_data/Lion", "normal_animal_data/Panda", "normal_animal_data/Tiger", "normal_animal_data/Zebra"]
    # sepia_images_paths = ["sepia_anime_data", "sepia_animal_data/Bear", "sepia_animal_data/Bird", "sepia_animal_data/Cat", "sepia_animal_data/Cow", "sepia_animal_data/Deer", "sepia_animal_data/Dog",
    #                       "sepia_animal_data/Dolphin", "sepia_animal_data/Elephant", "sepia_animal_data/Giraffe", "sepia_animal_data/Horse", "sepia_animal_data/Kangaroo",
    #                       "sepia_animal_data/Lion", "sepia_animal_data/Panda", "sepia_animal_data/Tiger", "sepia_animal_data/Zebra"]
    # MANUAL
    # Accuracy: 0.5306122448979592
    # Precision: 0.7509371095376925
    # Recall: 0.5306122448979592
    # F1 Score: 0.367891156462585

    ANN_Classification.ann_classification(real_images_paths, sepia_images_paths, "tool")
    # CNN_manual.CNN_manual(real_images_paths, sepia_images_paths) NOT WORKING / NOT IMPLEMENTED



    # Hyper-Parameters Influence

    # hidden_layer_sizes=(15,), activation='tanh', max_iter=100, solver='sgd', verbose=1, random_state=1, learning_rate_init=.001
    # Accuracy: 0.9375
    # Precision: 0.9453125
    # Recall: 0.9375
    # F1 Score: 0.9377450980392157

    # MANUAL
    # hidden_layer_sizes=(15,), activation='relu', max_iter=100, verbose=5, random_state=1, learning_rate_init=.01
    # Accuracy: 0.90625
    # Precision: 0.907843137254902
    # Recall: 0.90625
    # F1 Score: 0.906158357771261








    # big folder "images" BEST RESULTS (deleted folder because it had aprox. 70.000 images ^_^)
    # Iteration 1, loss = 0.60984309
    # Iteration 2, loss = 0.22052522
    # Iteration 3, loss = 0.14609409
    # Iteration 4, loss = 0.10499679
    # Iteration 5, loss = 0.08010537
    # Iteration 6, loss = 0.06083375
    # Iteration 7, loss = 0.04811155
    # Iteration 8, loss = 0.04033556
    # Iteration 9, loss = 0.03045800
    # Iteration 10, loss = 0.02803362
    # Iteration 11, loss = 0.02489839
    # Iteration 12, loss = 0.02143529
    # Iteration 13, loss = 0.02010324
    # Iteration 14, loss = 0.01860799
    # Iteration 15, loss = 0.01533600
    # Iteration 16, loss = 0.01702296
    # Iteration 17, loss = 0.01455887
    # Iteration 18, loss = 0.01188414
    # Iteration 19, loss = 0.01078526
    # Iteration 20, loss = 0.00977928
    # Iteration 21, loss = 0.00990224
    # Iteration 22, loss = 0.00852190
    # Iteration 23, loss = 0.00888404
    # Iteration 24, loss = 0.00796374
    # Iteration 25, loss = 0.00756335
    # Iteration 26, loss = 0.00749086
    # Iteration 27, loss = 0.00738983
    # Iteration 28, loss = 0.00704109
    # Iteration 29, loss = 0.00649850
    # Iteration 30, loss = 0.00658161
    # Iteration 31, loss = 0.00592335
    # Iteration 32, loss = 0.00500304
    # Iteration 33, loss = 0.00507074
    # Iteration 34, loss = 0.00512367
    # Accuracy: 0.9618398677373642
    # Precision: 0.96590118
    # Recall: 0.95724138