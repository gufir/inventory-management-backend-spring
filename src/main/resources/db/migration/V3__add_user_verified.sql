-- 0. Add column is_verified
ALTER TABLE "user"
ADD COLUMN is_verified BOOLEAN NOT NULL DEFAULT FALSE;

-- 1. Create enum
CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

-- 2. Normalize existing data
UPDATE "user"
SET role = UPPER(role);

UPDATE "user"
SET role = 'USER'
WHERE role NOT IN ('ADMIN', 'USER');

-- 3. Alter column to enum
ALTER TABLE "user"
ALTER COLUMN "role" TYPE user_role
USING role::user_role;

-- 4. Set default value
ALTER TABLE "user"
ALTER COLUMN "role" SET DEFAULT 'USER';