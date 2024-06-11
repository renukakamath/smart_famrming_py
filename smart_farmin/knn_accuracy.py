import os
import pandas as pd
import numpy as np
from sklearn import datasets, linear_model


# data = pd.read_csv('data/data/crop_value_ds_new.csv')
data = pd.read_csv('data/data/crop_production1.csv')
array = data.values

dislis=[]
y=[]
for i in range(len(array)):
    # print(array[i][12])

    if array[i][6] not in dislis:
        dislis.append(array[i][6])
    y.append(dislis.index(array[i][6]))

print(dislis)
print(len(dislis))
print (len(y))
df = pd.DataFrame(array)


maindf = df[[0, 1, 2, 3, 4, 5]]
mainarray = maindf.values

train_y = y
from sklearn.neighbors import KNeighborsClassifier

knn = KNeighborsClassifier(n_neighbors=3)
from sklearn.model_selection import train_test_split
knn.fit(mainarray, train_y)

X_train, X_test, y_train, y_test = train_test_split(mainarray, train_y, test_size = 0.23)

pred=knn.predict(X_test)

from sklearn.metrics import confusion_matrix
cm=confusion_matrix(y_test, pred)
print("confusion matrix")
print(cm)

from sklearn import metrics

print("model accuracy:", metrics.accuracy_score(y_test, pred))
