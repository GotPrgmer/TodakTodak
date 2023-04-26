# python matplotlib를 사용하여 하트를 그리는 함수를 정의한다.
# 하트를 그리는 함수를 정의한다.

import matplotlib.pyplot as plt
import numpy as np

def heart():
    t = np.linspace(0, 2*np.pi, 1000)
    x = 16*np.sin(t)**3
    y = 13*np.cos(t) - 5*np.cos(2*t) - 2*np.cos(3*t) - np.cos(4*t)
    plt.plot(x, y, color='red', linewidth=2)
    plt.axis('equal')
    plt.axis('off')
    plt.show()

# 함수를 호출한다.
heart()

