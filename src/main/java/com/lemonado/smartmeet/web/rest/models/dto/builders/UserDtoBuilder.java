package com.lemonado.smartmeet.web.rest.models.dto.builders;

import com.lemonado.smartmeet.web.rest.models.dto.UserDto;

import java.time.OffsetDateTime;

public final class UserDtoBuilder {
        private long id;
        private String username;
        private String email;
        private OffsetDateTime deleteTimestamp;
        private OffsetDateTime validTokenTimestamp;

        public UserDtoBuilder() {
        }

        public UserDtoBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder withDeleteTimestamp(OffsetDateTime deleteTimestamp) {
            this.deleteTimestamp = deleteTimestamp;
            return this;
        }

        public UserDtoBuilder withValidTokenTimestamp(OffsetDateTime validTokenTimestamp) {
            this.validTokenTimestamp = validTokenTimestamp;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, username, email, deleteTimestamp, validTokenTimestamp);
        }
    }