package com.readingisgood;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.Validation;
import javax.validation.Validator;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractTestBase {

    @Autowired
    protected TestEntityManager entityManager;

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

}
