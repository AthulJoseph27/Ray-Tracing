from Coordinate_Geometry import Vector, Point, Color
from abc import ABC, abstractmethod


class Scene:

    def __init__(self):
        self.objects = []

    def add(self, obj):
        self.objects.append(obj)


class Shape(ABC):

    @abstractmethod
    def is_inside(self, p):
        pass

    @abstractmethod
    def is_on_surface(self, p):
        pass


class Sphere(Shape):

    def __init__(self, radius, center):
        self.radius = radius
        self.r2 = radius**2
        self.center = center  # Point

    def is_inside(self, p):
        v = Vector(self.center, p)
        v = v.magnitude**2

        return v <= self.r2

    def is_on_surface(self, p):
        v = Vector(self.center, p).magnitude**2
        return v == self.r2


class Square(Shape):

    def __init__(self, a, center):
        self.a = a
        self.center = center  # Point
        lx = center.x - a/2
        hx = center.x + a/2

        ly = center.y - a/2
        hy = center.y + a/2

        lz = center.z - a/2
        hz = center.z + a/2

        self.low = Point(lx, ly, lz)
        self.high = Point(hx, hy, hz)

        print(self.low)
        print(self.high)

    def is_inside(self, p):
        # print(self.low)
        # print(p)
        # print(self.high)
        # print((p.x <= self.high.x and p.x >= self.low.x))
        # print((p.y <= self.high.y and p.y >= self.low.y))
        # print((p.z <= self.high.z and p.z >= self.low.z))
        # print((p.x <= self.high.x and p.x >= self.low.x) and (
        #     p.y <= self.high.y and p.y >= self.low.y) and (p.z <= self.high.z and p.z >= self.low.z))
        # print()
        return (p.x <= self.high.x and p.x >= self.low.x) and (p.y <= self.high.y and p.y >= self.low.y) and (p.z <= self.high.z and p.z >= self.low.z)

    def is_on_surface(self, p):
        return (p.x == self.high.x and p.x == self.low.x) and (p.y == self.high.y and p.y == self.low.y) and (p.z == self.high.z and p.z == self.low.z)
