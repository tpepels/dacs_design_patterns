import requests


def get_pokemon_info(name):
    url = f"https://pokeapi.co/api/v2/pokemon/{name.lower()}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        print(f"Name: {data['name'].capitalize()}")
        print(f"Height: {data['height']}")
        print(f"Weight: {data['weight']}")
        print("Abilities:")
        for ability in data["abilities"]:
            print(f" - {ability['ability']['name']}")
    else:
        print(f"Error: {response.status_code} - Could not find Pokémon '{name}'")


if __name__ == "__main__":
    pokemon_name = input("Enter Pokémon name: ")
    get_pokemon_info(pokemon_name)
