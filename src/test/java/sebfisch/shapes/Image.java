package sebfisch.shapes;

import java.util.ArrayList;
import java.util.List;
import sebfisch.test.gen.random.RandomGenerator;
import sebfisch.util.Traversal;

/** An image contains an arbitrary number of shapes. */
public class Image {
  private List<Shape> shapes;

  /** Creates an empty image containing no shapes. */
  public Image() {
    shapes = new ArrayList();
  }

  /**
   * Provides access to the shapes in this image.
   *
   * @return list of shapes in this image
   */
  public List<Shape> getShapes() {
    return shapes;
  }

  /** Traversal for the shapes in an image. */
  public static final Traversal<Image, Shape> SHAPES =
      new Traversal.For<Image>().flatMap(Image::getShapes);

  /** Random generator for image arguments of unit tests. */
  public static class Gen extends RandomGenerator<Image> {
    private final int maxShapeCount = 5;
    private final Shape.Gen shapeGen = new Shape.Gen();

    @Override
    public Image get() {
      final Image result = new Image();
      int shapeCount = random.nextInt(maxShapeCount);
      while (shapeCount > 0) {
        result.getShapes().add(shapeGen.get());
        --shapeCount;
      }
      return result;
    }
  }
}
