package programmers.nbe5_7_1_8bit.domain.product.exception;

public class ProductException extends RuntimeException {

  public ProductException(String message) {
    super(message);
  }

  public static class ProductNotFoundException extends ProductException {

    public ProductNotFoundException() {
      super("존재하지 않는 상품입니다.");
    }
  }

  public static class RemovedProductException extends ProductException {

    public RemovedProductException() {
      super("삭제된 상품입니다.");
    }
  }

  public static class ProductImageNotFoundException extends ProductException {

    public ProductImageNotFoundException() {
      super("해당 상품에 이미지가 없습니다.");
    }
  }

  public static class ImageReadException extends ProductException {

    public ImageReadException(String filename) {
      super("파일을 읽을 수 없습니다: " + filename);
    }
  }
}