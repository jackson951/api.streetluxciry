-- Manual SQL to fix the delivery fields issue
-- Run this directly against your PostgreSQL database if the application migration doesn't work

-- Add delivery fields to the orders table
ALTER TABLE orders ADD COLUMN IF NOT EXISTS delivery_fee DECIMAL(12,2) NOT NULL DEFAULT 0.00;
ALTER TABLE orders ADD COLUMN IF NOT EXISTS is_delivery BOOLEAN;
ALTER TABLE orders ADD COLUMN IF NOT EXISTS shipping_address VARCHAR(400);

-- Update existing orders to have default values
UPDATE orders SET delivery_fee = 0.00 WHERE delivery_fee IS NULL;
UPDATE orders SET is_delivery = FALSE WHERE is_delivery IS NULL;

-- Verify the columns were added
SELECT column_name, data_type, is_nullable, column_default 
FROM information_schema.columns 
WHERE table_name = 'orders' 
AND column_name IN ('delivery_fee', 'is_delivery', 'shipping_address');