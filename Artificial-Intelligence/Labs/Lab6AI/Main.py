from Pb1.univariate_gradient_descent import univariate_gradient_descent
from Pb1.bivariate_gradient_descent import bivariate_gradient_descent
from Pb2.cancerous_tissues_classification import cancerous_tissues_classification
from Pb3.flower_preference_classification import flower_preference_classification
from Optional.cancerous_tissues_classification_cross_validation import cancerous_tissues_classification_cross_validation

import warnings; warnings.simplefilter('ignore')

if __name__ == "__main__":
    print("Hello World!")

    print("\nPb1a: Univariate Gradient Descent")
    # univariate_gradient_descent("batches")
    # STOCASTIC:
    # the learnt model: f(x) =  3.1994285956915123  +  2.1487678365481915  * x
    # prediction error (manual):  1.9008773201208433
    # prediction error (tool):  1.9008773201208433

    # BATCHES:
    # the learnt model: f(x) =  3.199548185216422  +  2.1489553125335217  * x
    # prediction error (manual):  1.8999752100241594
    # prediction error (tool):  1.8999752100241594

    print("\nPb1b: Bivariate Gradient Descent")
    # bivariate_gradient_descent("stocastic")
    # STOCASTIC:
    # the learnt model: f(x) =  -0.0014527924544318889  +  0.6978631617347402  * x1 +  0.30375393537641193  * x2
    # prediction error (manual):  0.2331793993161317
    # prediction error (tool):    0.23317939931613166

    # BATCHES:
    # the learnt model: f(x) =  -0.0011659051562902811  +  0.6979590980445686  * x1 +  0.3039745892925814  * x2
    # prediction error (manual):  0.23322521427524043
    # prediction error (tool):    0.2332252142752404

    print("\nPb2: Clasificarea tesuturilor cancerigene")
    # cancerous_tissues_classification("manual")
    # CU TOOL:
    # the learnt model: f(x) =  -0.9122440356107672  +  3.714265538441941  * x1 +  0.9215248354552286  * x2
    # Accuracy: 0.7982456140350878
    # The lesion is predicted to be malignant.

    # MANUAL:
    # the learnt model: f(x) =  -0.960872493582651  +  4.476084448566163  * x1 +  1.04464469245949  * x2
    # Accuracy: 0.8070175438596491
    # The lesion is predicted to be malignant.

    print("\nPb3: Ce fel de floare preferi?")
    # flower_preference_classification("manual")
    # CU TOOL:
    # the learnt model: f(x) =  -0.10971986973912605  +  -0.9687222535134714  * x1 +  1.216084167441676  * x2
    # Accuracy:  0.9666666666666667
    # Precision:  0.9666666666666667
    # Recall:  0.9666666666666667
    # The predicted species for the flower is:  Iris-setosa

    # MANUAL:
    # the learnt model: f(x) =  3.491314967123042  +  3.1000205007035575  * x1 +  -1.0350808485476393  * x2
    # Accuracy:  0.5666666666666667
    # Precision:  0.415
    # Recall:  0.5666666666666667
    # The predicted species for the flower is:  Iris-setosa

    print("\nOptional: Clasificarea tesuturilor cancerigene cu cross-validation")
    # cancerous_tissues_classification_cross_validation()
    # Accuracy:  0.887408787455364
    # the learnt model: f(x) =  -0.6994318055050132  +  3.336421768653383  * x1 +  0.8767014817409343  * x2
    # The lesion is predicted to be malignant.