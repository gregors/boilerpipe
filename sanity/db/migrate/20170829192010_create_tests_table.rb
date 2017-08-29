class CreateTestsTable < ActiveRecord::Migration[5.1]
  def change
    create_table :tests_tables do |t|
      t.string :test_name
      t.string :class_name
      t.string :method_name
      t.string :input
      t.string :output
      t.string :constructor
      t.string :setup
      t.string :notes
      t.string :original_location
    end
  end
end
