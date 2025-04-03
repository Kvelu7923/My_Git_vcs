import os
import pandas as pd
import json
import random
import sys
from robot.api.deco import keyword

print("Script started...")

# Check if script is run manually (not from Robot Framework)
if __name__ == "__main__":
    while True:
        try:
            duplicate_count = int(input("Enter number of copies to generate: "))
            if duplicate_count < 1:
                raise ValueError
            break
        except ValueError:
            print("Please enter a valid positive integer.")
    print(f"User entered: {duplicate_count}")
else:
    duplicate_count = 1  # Default for Robot Framework

def read_json_from_txt(input_file):
    """Reads structured JSON from a .txt file, supporting both JSON arrays and multi-line JSON objects."""
    with open(input_file, 'r', encoding='utf-8') as file:
        content = file.read().strip()
    
    try:
        data = json.loads(content)  # Load full JSON content at once
        if isinstance(data, list):
            return data
        else:
            print(f"ERROR: JSON in {input_file} is not a list!")
            return []
    except json.JSONDecodeError as e:
        print(f"ERROR: Failed to parse JSON in {input_file}: {e}")
        return []

def ensure_csv_headers(csv_file, new_headers):
    """Ensures the CSV file has the required headers, updating them if new ones are found."""
    if not os.path.exists(csv_file) or os.path.getsize(csv_file) == 0:
        print(f"{csv_file} is empty or missing. Creating with headers: {new_headers}")
        pd.DataFrame(columns=new_headers).to_csv(csv_file, index=False)
    else:
        existing_df = pd.read_csv(csv_file)
        existing_headers = list(existing_df.columns)
        if set(new_headers) - set(existing_headers):
            print(f"Updating headers in {csv_file} to include new fields: {set(new_headers) - set(existing_headers)}")
            updated_headers = existing_headers + list(set(new_headers) - set(existing_headers))
            existing_df = existing_df.reindex(columns=updated_headers, fill_value="")
            existing_df.to_csv(csv_file, index=False)

def generate_final_csv(template_csv, parsed_data, output_csv, duplicate_count=1):
    """Generates an updated CSV by creating headers dynamically and filling in parsed data."""
    
    all_headers = set()
    for entry in parsed_data:
        all_headers.update(entry.keys())
    all_headers = sorted(all_headers)  # Ensure consistent column order
    
    ensure_csv_headers(template_csv, all_headers)
    
    print(f"Checking file: {output_csv}")
    
    total_rows = len(parsed_data) * duplicate_count
    data_list = []

    prefixes = ["Prathik", "Rahona", "Tester"]
    suffixes = ["ch001", "x001", "z002"]

    for entry in parsed_data:
        for _ in range(duplicate_count):
            row_data = {key: entry.get(key, "") for key in all_headers}  # Fill with JSON data
            
            if "kyc_id_number" in all_headers:
                rand_number = random.randint(10000, 99999)
                prefix = random.choice(prefixes)
                suffix = random.choice(suffixes)
                row_data["kyc_id_number"] = f"{prefix}{rand_number}{suffix}"
            
            data_list.append(row_data)

    updated_df = pd.DataFrame(data_list, columns=all_headers)  # Ensure consistent order
    try:
        updated_df.to_csv(output_csv, mode="a", index=False, header=not os.path.exists(output_csv) or os.path.getsize(output_csv) == 0)
        print(f"{os.path.basename(output_csv)} updated with {total_rows} rows.")
    except PermissionError:
        print(f"ERROR: Permission denied when writing to {output_csv}. Close the file if open and try again.")

@keyword("Update CSVs From Notepad")
def update_csvs_from_txt(input_dir, search_dir, duplicate_count=1):
    """Processes all .txt files in the input directory and updates corresponding CSVs."""

    duplicate_count = int(duplicate_count)  # Convert to int (Robot Framework passes arguments as strings)

    print(f"Using duplicate count: {duplicate_count}")  # Confirm received value

    if not os.path.exists(input_dir):
        print(f"ERROR: Input directory '{input_dir}' not found!")
        return

    for file_name in os.listdir(input_dir):
        if file_name.endswith(".txt"):
            org_name = file_name.replace(".txt", "")  # Extract ORG name
            input_file_path = os.path.join(input_dir, file_name)
            csv_template_path = os.path.join(search_dir, f"{org_name}.csv")
            output_csv_path = csv_template_path  # Output will be the same as input CSV

            print(f" Processing {file_name} -> {org_name}.csv")

            parsed_data = read_json_from_txt(input_file_path)
            if parsed_data:
                generate_final_csv(csv_template_path, parsed_data, output_csv_path, duplicate_count)
            else:
                print(f"⚠️ Skipping {file_name} due to invalid or empty data.")

    print(" CSV update process completed.")
# Example execution (Remove this if integrating into Robot Framework)
if __name__ == "__main__":
    input_txt_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "TestData")
    search_csv_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "TestData")
    
    update_csvs_from_txt(input_txt_dir, search_csv_dir, duplicate_count)
    print("Script finished.")
    print("Script completed successfully!")
