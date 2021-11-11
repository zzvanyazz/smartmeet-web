package com.lemonado.smartmeet.web.rest.models.dto.builders;

import com.lemonado.smartmeet.web.rest.models.dto.RoleDto;

import java.time.OffsetDateTime;

public final class RoleDtoBuilder {
        private long id;
        private String name;
        private String description;
        private OffsetDateTime createTimestamp;
        private OffsetDateTime updateTimestamp;

        public RoleDtoBuilder() {
        }


        public RoleDtoBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public RoleDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RoleDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public RoleDtoBuilder withCreateTimestamp(OffsetDateTime createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public RoleDtoBuilder withUpdateTimestamp(OffsetDateTime updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public RoleDto build() {
            return new RoleDto(id,name,description,createTimestamp,updateTimestamp);
        }
    }