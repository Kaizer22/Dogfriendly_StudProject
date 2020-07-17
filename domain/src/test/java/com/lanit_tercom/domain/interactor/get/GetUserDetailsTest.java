package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetUserDetailsTest {
    private GetUserDetailsUseCase useCase;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    void testRunMethodWithGetUser() {
        userRepository.getUserById("", Mockito.mock(UserRepository.UserDetailsCallback.class));
    }
}