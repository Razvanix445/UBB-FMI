import os
from PIL import Image

def apply_sepia(input_directory_path, output_directory_path):
    if not os.path.exists(output_directory_path):
        os.makedirs(output_directory_path)

    for filename in os.listdir(input_directory_path):
        if filename.endswith(".jpg") or filename.endswith(".png"):
            image_path = os.path.join(input_directory_path, filename)
            output_path = os.path.join(output_directory_path, filename)

            image = Image.open(image_path)
            if image.mode != 'RGB':
                image = image.convert('RGB')
            width, height = image.size
            pixels = image.load()

            for py in range(height):
                for px in range(width):
                    r, g, b = image.getpixel((px, py))

                    tr = int(0.393 * r + 0.769 * g + 0.189 * b)
                    tg = int(0.349 * r + 0.686 * g + 0.168 * b)
                    tb = int(0.272 * r + 0.534 * g + 0.131 * b)

                    if tr > 255:
                        tr = 255

                    if tg > 255:
                        tg = 255

                    if tb > 255:
                        tb = 255

                    pixels[px, py] = (tr, tg, tb)

            image.save(output_path)

apply_sepia('images', 'sepia_images')
# apply_sepia('normal_animal_data/Giraffe', 'sepia_animal_data/Giraffe')
# apply_sepia('normal_animal_data/Horse', 'sepia_animal_data/Horse')
# apply_sepia('normal_animal_data/Kangaroo', 'sepia_animal_data/Kangaroo')
# apply_sepia('normal_animal_data/Lion', 'sepia_animal_data/Lion')
# apply_sepia('normal_animal_data/Panda', 'sepia_animal_data/Panda')
# apply_sepia('normal_animal_data/Tiger', 'sepia_animal_data/Tiger')
# apply_sepia('normal_animal_data/Zebra', 'sepia_animal_data/Zebra')