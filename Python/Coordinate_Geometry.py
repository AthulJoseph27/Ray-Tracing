import math
import numpy as np


class Color:
    def __init__(self, r=0, g=0, b=0, a=0):
        self.r = r
        self.g = g
        self.b = b
        self.a = a

    def __str__(self):
        return f"({self.r},{self.b},{self.g},{self.a})"


class Point:
    def __init__(self, x=0, y=0, z=0, color=Color()):
        self.x = x
        self.y = y
        self.z = z
        self.color = color

    def __str__(self):
        return f'( x: {self.x} , y: {self.y} , z: {self.z} )'


class Vector:
    def __init__(self, p1, p2, color=Color()):
        self.i = p2.x - p1.x
        self.j = p2.y - p1.y
        self.k = p2.z - p1.z
        self.color = color
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def __str__(self):
        return f'{"- " if self.i<0 else ""}{abs(self.i)}i {"-" if self.j<0 else "+"} {abs(self.j)}j {"-" if self.k<0 else "+"} {abs(self.k)}k'

    def __truediv__(self, other):
        v = Vector(Point(), Point(self.i/other, self.j/other, self.k/other))
        return v

    def __mul__(self, other):
        v = Vector(Point(), Point(self.i*other, self.j*other, self.k*other))
        return v

    def __add__(self, other):
        v = Vector(Point(), Point(self.i+other.i,
                   self.j+other.j, self.k+other.k))
        return v

    def __sub__(self, other):
        v = Vector(Point(), Point(self.i-other.i,
                   self.j-other.j, self.k-other.k))
        return v

    def unit_vector(self):
        return self/self.magnitude

    def rotate(self, alpha, beta, gamma):

        rx = np.array([[1, 0, 0], [0, math.cos(alpha), -
                      math.sin(alpha)], [0, math.sin(alpha), math.cos(alpha)]])
        ry = np.array([[math.cos(beta), 0, math.sin(beta)], [
                      0, 1, 0], [-math.sin(beta), 0, math.cos(beta)]])
        rz = np.array([[math.cos(gamma), -math.sin(gamma), 0],
                      [math.sin(gamma), math.cos(gamma), 0], [0, 0, 1]])

        v = np.array([self.i, self.j, self.k])

        r = np.dot(rx, ry)
        r = np.dot(r, rz)
        r = np.dot(r, v)

        self.i = r[0]
        self.j = r[1]
        self.k = r[2]
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def rotateX(self, alpha):
        rx = np.array([[1, 0, 0], [0, math.cos(alpha), -
                      math.sin(alpha)], [0, math.sin(alpha), math.cos(alpha)]])

        v = np.array([self.i, self.j, self.k])

        r = np.dot(rx, v)

        self.i = r[0]
        self.j = r[1]
        self.k = r[2]
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def rotateY(self, beta):

        ry = np.array([[math.cos(beta), 0, math.sin(beta)], [
                      0, 1, 0], [-math.sin(beta), 0, math.cos(beta)]])

        v = np.array([self.i, self.j, self.k])

        r = np.dot(ry, v)

        self.i = r[0]
        self.j = r[1]
        self.k = r[2]
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def rotateZ(self, gamma):

        rz = np.array([[math.cos(gamma), -math.sin(gamma), 0],
                      [math.sin(gamma), math.cos(gamma), 0], [0, 0, 1]])

        v = np.array([self.i, self.j, self.k])

        r = np.dot(rz, v)

        self.i = r[0]
        self.j = r[1]
        self.k = r[2]
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def custom_rotate(self, r_matrix):

        v = np.array([self.i, self.j, self.k])
        r = np.dot(r_matrix, v)

        self.i = r[0]
        self.j = r[1]
        self.k = r[2]
        self.magnitude = self.get_magnitude()
        self.alpha = 0 if self.magnitude == 0 else math.acos(
            self.i/self.magnitude)
        self.beta = 0 if self.magnitude == 0 else math.acos(
            self.j/self.magnitude)
        self.gamma = 0 if self.magnitude == 0 else math.acos(
            self.k/self.magnitude)

    def get_magnitude(self):
        return math.sqrt(self.i**2 + self.j**2 + self.k**2)

    def dot_product(self, v2):
        return self.i*v2.i + self.j*v2.j + self.k*v2.k

    def cross_product(self, v2, assign=True):

        p = Point(self.j*v2.k - self.k*v2.j, self.i*v2.k -
                  self.k*v2.i, self.i*v2.j - self.j*v2.i)

        v = Vector(Point(), p)

        if assign:
            self.i = v.i
            self.j = v.j
            self.k = v.k
            self.magnitude = v.magnitude
            self.alpha = v.alpha
            self.beta = v.beta
            self.gamma = v.gamma

        return v

    def angle_between(self, v2):

        dot_pd = self.dot_product(v2)

        return math.acos(dot_pd/(self.magnitude * v2.magnitude))


class Quaternion:

    def __init__(self, normal=Vector(Point(), Point(1, 1, 1)), theta=0):

        self.r = math.cos(theta/2)
        self.v = normal*math.sin(theta/2)

    def get_inverse(self):
        q = Quaternion()
        q.r = self.r
        q.v = self.v*-1
        return q

    def __mul__(self, other):
        w = self.r*other.r - self.v.dot_product(other.v)
        v = other.v*self.r + self.v*other.r + \
            other.v.cross_product(self.v, False)
        q = Quaternion()
        q.v = v
        q.r = w
        return q

    def __str__(self):
        return f"(r: {self.r} v: {self.v})"


class Plane:

    def __init__(self, width, height, rx=0, ry=0, rz=0, order=(0, 1, 2)):
        self.width = width
        self.height = height
        self.rx = rx
        self.ry = ry
        self.rz = rz
        self.order = order

    def get_tranformed_coordinate(self, point):

        # qx = Quaternion(Vector(Point(), Point(1, 0, 0)), self.rx)
        # qy = Quaternion(Vector(Point(), Point(0, 1, 0)), self.ry)
        # qz = Quaternion(Vector(Point(), Point(0, 0, 1)), self.rz)

        # mp = {}
        # mp[0] = qx
        # mp[1] = qy
        # mp[2] = qz

        # q = mp[self.order[0]] * mp[self.order[1]] * mp[self.order[2]]

        # vp = Vector(Point(), point)
        # qp = Quaternion()
        # qp.r = 0
        # qp.v = vp
        # q_inverse = q.get_inverse()

        # result = q*qp*q_inverse

        v = Vector(Point(), point)

        for i in self.order:
            if i == 0:
                v.rotateX(self.rx)
            elif i == 1:
                v.rotateY(self.ry)
            else:
                v.rotateZ(self.rz)

        p = Point()
        p.x = v.i
        p.y = v.j
        p.z = v.k

        return p
