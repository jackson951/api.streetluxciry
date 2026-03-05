-- Add delivery fields to the orders table
ALTER TABLE orders ADD COLUMN IF NOT EXISTS delivery_fee DECIMAL(12,2) NOT NULL DEFAULT 0.00;
ALTER TABLE orders ADD COLUMN IF NOT EXISTS is_delivery BOOLEAN;
ALTER TABLE orders ADD COLUMN IF NOT EXISTS shipping_address VARCHAR(400);

-- Update existing orders to have default values
UPDATE orders SET delivery_fee = 0.00 WHERE delivery_fee IS NULL;
UPDATE orders SET is_delivery = FALSE WHERE is_delivery IS NULL;