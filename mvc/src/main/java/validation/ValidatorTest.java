package validation;

import model.Post;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public class ValidatorTest {

    public static void main(String[] args) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        Post post = new Post(1L, "", null);

        var violations = validator.validate(post);

        for (ConstraintViolation<Post> violation : violations) {
            System.out.println(violation.getMessage());
        }

    }


}
