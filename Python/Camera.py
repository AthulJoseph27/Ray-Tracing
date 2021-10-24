from Shapes import Color, Scene, Sphere, Square
from Coordinate_Geometry import Plane, Point
import math


class Camera:

    def __init__(self, width, height, scene=None):
        self.widht = width
        self.height = height
        self.rx = 0
        self.ry = 0
        self.rz = 0
        self.pi_2 = 2 * math.pi
        self.plane = Plane(width, height, self.rx, self.ry, self.rz)
        self.screen = [[Color(0, 0, 0, 1) for i in range(width)]
                       for j in range(height)]
        self.scene = Scene() if scene == None else scene
        if scene == None:
            self.scene.add(Sphere(100, Point(width/4, 80, height/2)))
            self.scene.add(Sphere(100, Point(3*width/4, 85, height/2)))
            # self.scene.add(Square(100, Point(width/2, 0, height/2)))

    def add_x(self, delta):
        self.rx += delta
        if self.rx > self.pi_2:
            x = self.rx//self.pi_2
            self.rx -= x*self.pi_2
        self.plane.rx = self.rx

    def add_y(self, delta):
        self.ry += delta
        if self.ry > self.pi_2:
            y = self.ry//self.pi_2
            self.ry -= y*self.pi_2
        self.plane.ry = self.ry

    def add_z(self, delta):
        self.rz += delta
        if self.rz > self.pi_2:
            z = self.rz//self.pi_2
            self.rz -= z*self.pi_2
        self.plane.rz = self.rz

    def fill_screen(self):

        for y in range(self.height):
            for x in range(self.widht):
                for obj in self.scene.objects:
                    p = self.plane.get_tranformed_coordinate(Point(x, 0, y))
                    if obj.is_on_surface(p):
                        self.screen[y][x] = Color(1, 1, 1, 1)
