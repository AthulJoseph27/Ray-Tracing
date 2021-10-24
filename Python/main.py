import pygame
from Camera import Camera

pygame.init()
clock = pygame.time.Clock()

WIDTH = 400
HEIGHT = 400
win = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption('Ray Tracing')
win.fill((0, 0, 0))


def loop():

    exit = False

    cam = Camera(WIDTH, HEIGHT)

    is_dragging = False
    mouse_x = 0
    mouse_y = 0

    cam.fill_screen()

    for y in range(cam.height):
        for x in range(cam.widht):
            c = cam.screen[y][x]
            win.set_at(
                (x, y), (int(c.r*255), int(c.g*255), int(c.g*255), c.a))

    while True:

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit = True
                break
            # elif event.type == pygame.MOUSEBUTTONDOWN:
            #     if event.button == 1:
            #         is_dragging = True
            #         mouse_x, mouse_y = event.pos

            # elif event.type == pygame.MOUSEBUTTONUP:
            #     if event.button == 1:
            #         is_draging = False

            # elif event.type == pygame.MOUSEMOTION:
            #     if is_dragging:
            #         new_mouse_x, new_mouse_y = event.pos
            #         cam.add_x(new_mouse_x-mouse_x)
            #         cam.add_y(new_mouse_y-mouse_y)
            #         mouse_x = new_mouse_x
            #         mouse_y = new_mouse_y

        if exit:
            break

        pygame.display.update()


def main():
    loop()


# if __name__ == 'main':
main()
