import xmltodict
import matplotlib.pyplot as plt

def generate_pie_chart(robot_output="output.xml", output_chart="tags_pie_chart.png"):
    with open(robot_output, "r", encoding="utf-8") as file:
        data = xmltodict.parse(file.read())

    tag_counts = {}

    # Extract test cases and their tags
    for suite in data["robot"]["suite"]["test"]:
        if "tag" in suite:
            tags = suite["tag"] if isinstance(suite["tag"], list) else [suite["tag"]]
            for tag in tags:
                tag_counts[tag] = tag_counts.get(tag, 0) + 1

    if not tag_counts:
        print("No tags found in the report.")
        return

    # Generate Pie Chart
    plt.figure(figsize=(8, 8))
    plt.pie(tag_counts.values(), labels=tag_counts.keys(), autopct="%1.1f%%", startangle=140)
    plt.title("Test Case Tags Distribution")
    plt.savefig(output_chart)
    print(f"📊 Pie chart saved as {output_chart}")
if __name__ == "__main__":
    input_txt_dir = "resources/TestData"
    search_csv_dir = "resources/TestData"
    
    update_csvs_from_txt(input_txt_dir, search_csv_dir)
    
    print("✅ Script finished.")
    print("🚀 Script completed successfully!")
    
    print("📊 Generating Pie Chart for test tags...")
    generate_pie_chart()  # Now this should work correctly
