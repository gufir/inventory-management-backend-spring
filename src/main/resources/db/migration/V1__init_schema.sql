-- ENUM TYPE
CREATE TYPE stock_movement_type AS ENUM ('IN', 'OUT');

-- USER TABLE
CREATE TABLE "user" (
  "user_id" uuid PRIMARY KEY NOT NULL,
  "username" varchar UNIQUE NOT NULL,
  "hashed_password" varchar NOT NULL,
  "email" varchar UNIQUE NOT NULL,
  "role" varchar NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT now(),
  "created_by" uuid NOT NULL,
  "updated_at" timestamptz NOT NULL DEFAULT now(),
  "updated_by" uuid NOT NULL,
  "deleted_at" timestamptz DEFAULT NULL,
  "deleted_by" uuid DEFAULT NULL
);

-- CATEGORY
CREATE TABLE "category" (
  "category_id" uuid PRIMARY KEY NOT NULL,
  "category_name" varchar NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT now(),
  "created_by" uuid NOT NULL,
  "updated_at" timestamptz NOT NULL DEFAULT now(),
  "updated_by" uuid NOT NULL,
  "deleted_at" timestamptz DEFAULT NULL,
  "deleted_by" uuid DEFAULT NULL
);

-- ITEM
CREATE TABLE "item" (
  "item_id" uuid PRIMARY KEY NOT NULL,
  "item_name" varchar NOT NULL,
  "item_code" varchar UNIQUE NOT NULL,
  "stock" int NOT NULL CHECK ("stock" >= 0),
  "category_id" uuid NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT now(),
  "created_by" uuid NOT NULL,
  "updated_at" timestamptz NOT NULL DEFAULT now(),
  "updated_by" uuid NOT NULL,
  "deleted_at" timestamptz DEFAULT NULL,
  "deleted_by" uuid DEFAULT NULL
);

-- SUPPLIER
CREATE TABLE "supplier" (
  "supplier_id" uuid PRIMARY KEY NOT NULL,
  "supplier_name" varchar NOT NULL,
  "address" varchar NOT NULL,
  "phone" varchar NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT now(),
  "created_by" uuid NOT NULL,
  "updated_at" timestamptz NOT NULL DEFAULT now(),
  "updated_by" uuid NOT NULL,
  "deleted_at" timestamptz DEFAULT NULL,
  "deleted_by" uuid DEFAULT NULL
);

-- STOCK MOVEMENT
CREATE TABLE "stock_movement" (
  "movement_id" uuid PRIMARY KEY NOT NULL,
  "item_id" uuid NOT NULL,
  "supplier_id" uuid,
  "type" stock_movement_type NOT NULL,
  "quantity" int NOT NULL CHECK ("quantity" > 0),
  "created_at" timestamptz NOT NULL DEFAULT now(),
  "created_by" uuid NOT NULL,
  "updated_at" timestamptz NOT NULL DEFAULT now(),
  "updated_by" uuid NOT NULL,
  "deleted_at" timestamptz DEFAULT NULL,
  "deleted_by" uuid DEFAULT NULL
);

-- FOREIGN KEYS
ALTER TABLE "item"
ADD CONSTRAINT "fk_item_category"
FOREIGN KEY ("category_id")
REFERENCES "category" ("category_id");

ALTER TABLE "stock_movement"
ADD CONSTRAINT "fk_stock_item"
FOREIGN KEY ("item_id")
REFERENCES "item" ("item_id");

ALTER TABLE "stock_movement"
ADD CONSTRAINT "fk_stock_supplier"
FOREIGN KEY ("supplier_id")
REFERENCES "supplier" ("supplier_id");