package com.ai.creavision.utils

import com.ai.creavision.R
import com.ai.creavision.domain.model.ArtStyle
import com.ai.creavision.domain.model.ArtStylePrompt

object Constants {

    //const val BASE_URL = "https://api.openai.com/v1/images/"

    const val BASE_URL = "https://api.replicate.com/v1/"

    private const val REWARDED_AD = "ca-app-pub-3940256099942544/5224354917"




    var PROMPTS = mapOf(
        "Space art" to ArtStylePrompt(R.drawable.space_art,"galactic style {prompt} . nebula, constellation, cosmic, celestial, highly detailed, starry","blurry, grainy, deformed, photo-realistic, low-contrast, terrestrial"),
        "Romantic" to ArtStylePrompt(R.drawable.romantic,"romantic style {prompt} . emotional, dramatic, nature-focused, detailed","unemotional, flat, city-focused, low detail"),
        "Pixelated" to ArtStylePrompt(R.drawable.pixelated,"pixelated style {prompt} . retro, low-res, digital, blocky","modern, high-res, organic, smooth"),
        "Futuristic" to ArtStylePrompt(R.drawable.futuristic,"futuristic {prompt} . advanced, high-tech, sleek, modern","old, low-tech, chunky, historical"),
        "Surrealist" to ArtStylePrompt(R.drawable.surrealist,"surrealist {prompt} . dream-like, bizarre, irrational, highly detailed","realistic, mundane, rational, low detail"),
        "Photorealistic" to ArtStylePrompt(R.drawable.photorealistic,"photorealistic {prompt} . highly detailed, lifelike, precise, accurate","abstract, low detail, unrealistic, inaccurate"),
        "Victorian" to ArtStylePrompt(R.drawable.victorian,"Victorian style {prompt} . 19th century, ornate, romantic, highly detailed","21st century, minimal, unemotional, undetailed"),
        "Expressionist" to ArtStylePrompt(R.drawable.expressionist,"expressionist style {prompt} . emotional, intense, vibrant, highly detailed","emotionless, calm, muted, low detail"))


    val ART_STYLES = PROMPTS.map { (name, prompt) ->
        ArtStyle(prompt.image, name)
    }


    val RANDOM_PROMPTS = listOf(
        "A surreal landscape with floating islands covered in lush greenery, cascading waterfalls pouring into the sky, and glowing bioluminescent plants illuminating the surroundings under a sky filled with vibrant, swirling auroras.",
        "A futuristic cityscape at night, featuring towering skyscrapers with neon lights, flying cars zipping through the air, holographic advertisements projecting from buildings, and robots walking among the people.",
        "A fierce dragon warrior clad in ornate, spiked armor, wielding a flaming sword, standing in a mystical forest with ancient runes glowing in the background and a dragon perched on a nearby cliff.",
        "A complex steampunk contraption with brass gears, steam pipes, and a large clock face, set in a Victorian-era workshop filled with blueprints, mechanical parts, and a scientist wearing goggles and a leather apron.",
        "An alien planet with unusual rock formations, multi-colored vegetation, and strange, bioluminescent creatures roaming the landscape, all under a sky with multiple moons and a distant galaxy visible.",
        "A Renaissance-style painting of a royal banquet, with elegantly dressed figures in lavish clothing, lavishly decorated tables with golden candelabras, and a grand hall with chandeliers and intricate tapestries.",
        "A cute, fluffy, rainbow-colored unicorn with big, sparkling eyes, prancing in a meadow full of colorful flowers and butterflies, with a backdrop of a magical castle in the distance.",
        "An epic medieval battle scene with knights in shining armor, clashing swords, warhorses charging across a vast battlefield, and a castle under siege in the background with flaming arrows lighting up the sky.",
        "A vibrant underwater scene with colorful coral reefs, schools of fish swimming among the corals, a majestic mermaid gracefully moving through the water, and sunlight filtering down from the surface.",
        "A detailed depiction of the signing of the Declaration of Independence, with founding fathers in period clothing gathered around a wooden table, quill pens in hand, and a grand hall filled with sunlight streaming through tall windows.",
        "A serene Japanese garden with cherry blossom trees in full bloom, a koi pond with brightly colored fish, a traditional tea house, and a stone lantern casting a soft glow in the twilight.",
        "A cozy cabin in the woods during a snowfall, with smoke gently rising from the chimney, warm lights glowing from the windows, and a snowman standing guard outside.",
        "A massive space station orbiting a distant planet, with astronauts performing a spacewalk, futuristic spacecraft docking, and a stunning view of a nebula in the background.",
        "A futuristic robot companion helping a child with homework in a high-tech home, with holographic displays, advanced gadgets, and a sleek, modern interior design.",
        "A magical library with towering bookshelves filled with ancient tomes, floating candles providing soft light, and enchanted books flying around as a wizard reads by a fireplace.",
        "A bustling medieval market with merchants selling a variety of goods, townsfolk milling about, colorful stalls filled with exotic items, and a bard playing a lute near a fountain.",
        "A majestic lion sitting atop a rock, overlooking the savannah at sunset, with a golden mane blowing in the wind and a herd of zebras grazing in the distance.",
        "A vintage 1950s diner with neon signs, classic cars parked outside, patrons enjoying milkshakes and burgers, a jukebox playing rock and roll, and waitresses in poodle skirts.",
        "A whimsical fairy village hidden in a forest, with tiny houses made from mushrooms and tree stumps, fairies flying around, and glowing lanterns hanging from the trees.",
        "A retro-futuristic scene with people in 1960s-inspired space suits exploring a Martian landscape, with vintage rockets, domed habitats, and red, rocky terrain under a pink sky.",
        "A pirate ship battling a colossal sea monster during a stormy night, with crashing waves, lightning illuminating the sky, and pirates bravely fighting on deck.",
        "A serene beach scene with turquoise waters gently lapping against the shore, palm trees swaying in the breeze, a hammock strung between two trees, and seashells scattered on the sand.",
        "A grand ballroom filled with dancers in elegant attire, chandeliers hanging from the ceiling, a live orchestra playing classical music, and large windows revealing a starry night outside.",
        "A high-tech laboratory with scientists in white coats working on cutting-edge experiments, advanced computers, robotic arms, and glowing screens displaying data.",
        "A medieval blacksmith forging a sword in his workshop, with sparks flying, molten metal glowing in the forge, and the rhythmic sound of hammer on anvil.",
        "A lush tropical rainforest with towering trees, dense vegetation, exotic animals like parrots and monkeys, and a waterfall cascading into a crystal-clear pool.",
        "A picturesque mountain village with cozy cottages, winding cobblestone paths, snow-capped peaks in the background, and smoke curling from chimneys.",
        "A vibrant street festival with colorful decorations, performers in elaborate costumes, food stalls offering a variety of cuisines, and a lively crowd enjoying the festivities.",
        "A serene lake surrounded by autumn foliage, with vibrant red and orange leaves reflecting on the water, a rowboat gently floating, and a mist rising from the surface.",
        "A mystical cave with glowing crystals lining the walls, underground rivers flowing, ancient carvings depicting forgotten tales, and a treasure chest half-buried in the sand.",
        "A vintage train traveling through a snowy landscape, with steam billowing from the engine, passengers peering out the windows, and a conductor in an old-fashioned uniform.",
        "A bustling city street in the 1920s with flapper dresses, classic cars, art deco buildings, people enjoying jazz music, and speakeasies with neon signs.",
        "A whimsical treehouse village with rope bridges connecting the houses, lanterns hanging from branches, children playing, and the soft glow of fireflies in the evening.",
        "A sleek, sci-fi spaceship interior with advanced technology, panoramic views of space, crew members in futuristic uniforms, and control panels with holographic displays.",
        "A serene monastery high in the mountains, with monks meditating, prayer flags fluttering in the wind, and a breathtaking view of snow-capped peaks.",
        "A fantasy castle perched on a cliff, surrounded by clouds, with turrets and towers reaching into the sky, a dragon flying in the distance, and a drawbridge over a moat.",
        "A vintage circus scene with acrobats performing daring feats, clowns entertaining the crowd, a big top tent filled with spectators, and a ringmaster announcing the acts.",
        "A magical forest glade with fairies flitting about, glowing mushrooms casting a soft light, a crystal-clear stream flowing through, and ancient trees with gnarled roots.",
        "A bustling ancient Roman marketplace with citizens in togas, marble statues lining the streets, vendors selling goods, and a lively atmosphere of trade and conversation.",
        "A futuristic city park with robotic gardeners tending to the plants, holographic displays providing information, people relaxing on floating benches, and children playing with interactive art installations.",
        "A medieval knight on horseback, jousting in a tournament, with a cheering crowd, colorful banners fluttering in the wind, and the intense focus of the competitors.",
        "A serene Zen garden with meticulously raked sand patterns, bonsai trees, a stone lantern casting a gentle glow, and a small pond with koi fish swimming peacefully.",
        "A bustling Parisian café with people enjoying coffee and pastries, street artists painting the scene, the Eiffel Tower visible in the distance, and the warm glow of street lamps.",
        "A cozy reading nook by a window, with a comfy chair, a stack of books, a blanket draped over the armrest, and a view of a blooming garden outside.",
        "A futuristic underwater city with glass domes, colorful sea life, advanced submarines, and people in high-tech diving suits exploring the depths.",
        "A vibrant carnival at night with rides spinning, games being played, colorful lights illuminating the scene, and people enjoying cotton candy and popcorn.",
        "A peaceful countryside scene with rolling hills, a quaint farmhouse, a windmill turning slowly in the breeze, and a flock of sheep grazing contentedly.",
        "A fantasy sorcerer's tower with magical artifacts on shelves, bubbling potions in glass bottles, a spellbook on a pedestal, and a mystical aura surrounding the room.",
        "A bustling New York City street in the 1980s with neon signs, yellow taxis honking, people in fashionable outfits, and street performers entertaining passersby.",
        "A serene desert oasis with palm trees providing shade, a clear blue pond reflecting the sky, and a camel resting near a Bedouin tent.",
        "A futuristic airport with flying cars landing, advanced security systems scanning travelers, and people in sleek, modern attire moving through the terminal.",
        "A magical winter wonderland with ice castles glittering, snow fairies dancing in the air, sparkling snowflakes falling gently, and children building snowmen.",
        "A cozy bookstore with wooden shelves filled with books, a cat curled up on a chair, a warm fireplace, and a soft reading light illuminating a comfortable armchair.",
        "A vibrant Indian market with colorful fabrics hanging from stalls, the aroma of spices filling the air, vendors calling out their wares, and people bustling through the narrow streets.",
        "A sci-fi research facility on a distant planet with alien landscapes visible through the windows, scientists in advanced suits conducting experiments, and high-tech equipment lining the walls.",
        "A serene vineyard in the Italian countryside with rows of grapevines stretching out under the sun, a stone villa overlooking the fields, and a wine tasting table set up with glasses and bottles.",
        "A whimsical fairy tale scene with a gingerbread house in a forest, smoke curling from the chimney, candy canes lining the path, and a witch peeking out from the doorway.",
        "A bustling Moroccan bazaar with colorful rugs hanging from stalls, the scent of spices in the air, merchants selling intricate lanterns, and a snake charmer performing.",
        "A futuristic hospital with advanced medical equipment, robotic surgeons, patients being treated with holographic interfaces, and a serene, high-tech recovery room.",
        "A vibrant tropical beach party at sunset, with tiki torches illuminating the scene, people dancing to lively music, a barbecue grill sizzling, and colorful cocktails being served.",
        "A historical scene of an ancient Egyptian marketplace, with traders selling goods, people wearing traditional clothing, pyramids in the background, and a riverboat on the Nile.",
        "A fantasy world with floating castles in the sky, connected by magical bridges, winged creatures flying around, and lush landscapes visible far below.",
        "A serene winter cabin interior, with a roaring fireplace, cozy furniture, blankets and pillows, a hot cup of cocoa on a wooden table, and snow falling outside the window.",
        "A futuristic cityscape during the day, with sleek, reflective skyscrapers, drones delivering packages, people commuting in automated pods, and green parks interspersed among the buildings.",
        "A medieval banquet hall with a long wooden table filled with food, a roaring fireplace, knights and nobles feasting, and minstrels playing music.",
        "A bustling 1950s street scene, with classic cars parked along the curb, people dressed in period clothing, children playing hopscotch, and a soda shop with a bright neon sign.",
        "A magical forest with a hidden waterfall, surrounded by glowing flowers, enchanted creatures like unicorns and fairies, and a rainbow arching over the scene.",
        "A serene lake house at sunrise, with a wooden deck overlooking the water, a small boat tied to the dock, mist rising from the lake, and pine trees framing the view.",
        "A futuristic sports arena with a holographic scoreboard, athletes in advanced gear, a cheering crowd with light-up signs, and a game being played with hovering drones.",
        "A cozy mountain lodge interior, with rustic wooden furniture, a stone fireplace, snow gear hanging on the walls, and large windows showcasing a snowy landscape.",
        "A vibrant street market in a small European village, with vendors selling fresh produce, flowers, and baked goods, cobblestone streets, and colorful awnings.",
        "A fantasy underwater palace with mermaid guards, coral pillars, a throne made of seashells, and schools of fish swimming around the grand hall.",
        "A serene autumn forest path, with leaves in shades of red, orange, and yellow, a soft carpet of fallen leaves on the ground, and sunlight filtering through the trees.",
        "A futuristic research lab with scientists working on advanced technology, holographic data displays, robotic assistants, and experimental devices glowing with energy.",
        "A bustling harbor with ships docked, sailors unloading cargo, fish markets nearby, seagulls flying overhead, and the sea glistening in the sunlight.",
        "A serene mountain hot spring, with steam rising from the warm water, surrounded by snow-covered rocks, evergreen trees, and a clear blue sky above.",
        "A futuristic train station with magnetic levitation trains, automated ticketing systems, passengers with smart luggage, and sleek, modern architecture.",
        "A whimsical candy land with hills made of chocolate, candy cane trees, rivers of syrup, and gingerbread houses, with candy creatures roaming around.",
        "A serene prairie with wildflowers in bloom, a wooden fence running along the horizon, a barn in the distance, and a horse grazing in the field.",
        "A bustling Chinese New Year celebration with dragon dancers, red lanterns hanging from buildings, fireworks in the sky, and people enjoying festive food.",
        "A futuristic library with shelves of digital books, holographic reading stations, robotic librarians, and students studying with advanced tablets.",
        "A serene alpine meadow with wildflowers, a clear blue sky, a log cabin nestled among the trees, and a crystal-clear stream flowing through the scene.",
        "A vibrant Dia de los Muertos celebration with colorful altars, sugar skulls, marigold flowers, traditional music, and people in festive costumes and face paint.",
        "A futuristic theme park with gravity-defying rides, interactive holographic attractions, robot mascots, and visitors enjoying advanced virtual reality experiences.",
        "A cozy living room decorated for Christmas, with a decorated tree, stockings hung by the fireplace, gifts wrapped in colorful paper, and a family enjoying the festive atmosphere.",
        "A bustling medieval village during a festival, with jugglers and acrobats performing, market stalls selling crafts, people dancing in the square, and banners fluttering in the breeze.",
        "A futuristic botanical garden with bioluminescent plants, advanced irrigation systems, floating walkways, and visitors exploring the lush, vibrant environment.",
        "A serene lakeside campsite at dusk, with a tent set up near the water, a campfire crackling, stars beginning to appear in the sky, and the silhouette of pine trees against the twilight.",
        "A vibrant Holi festival scene with people covered in colorful powder, music playing, dancing, and a joyful atmosphere of celebration and unity.",
        "A futuristic university campus with students attending classes via holographic teachers, drone deliveries, green energy systems, and innovative architectural designs.",
        "A cozy seaside cottage with a view of the ocean, a hammock on the porch, seashell wind chimes, and the sound of waves crashing against the shore.",
        "A bustling Tokyo street at night, with neon signs, people in trendy fashion, vending machines, anime billboards, and the iconic Shibuya Crossing filled with pedestrians.",
        "A serene sunflower field at sunset, with rows of tall sunflowers facing the setting sun, a rustic wooden fence, and a clear sky painted with warm hues.",
        "A futuristic zoo with holographic information displays, habitats simulating various environments, robotic caretakers, and visitors interacting with augmented reality exhibits.",
        "A cozy attic room with slanted ceilings, a skylight revealing the night sky, stacks of old books, vintage furniture, and a sense of nostalgia in the air.",
        "A bustling Italian piazza with a central fountain, outdoor cafés, people enjoying gelato, historical buildings, and musicians playing lively tunes.",
        "A futuristic concert with holographic performers, a crowd wearing augmented reality glasses, dynamic light shows, and immersive sound experiences.",
        "A serene lavender field in Provence, with rows of blooming lavender stretching to the horizon, a stone farmhouse in the distance, and bees buzzing among the flowers.",
        "A whimsical Halloween scene with a haunted house, carved pumpkins glowing with candles, children in costumes trick-or-treating, and ghosts and goblins in the yard.",
        "A futuristic marina with sleek yachts, floating platforms, automated docking systems, and a backdrop of a modern cityscape with skyscrapers reflecting the sunset.",
        "A serene garden maze with tall hedges, colorful flowers, a central fountain, and a sense of mystery and adventure as one navigates through the twists and turns.",)


}