# Gradient Gladiators

> *A low-poly 3D arena brawler where every enemy is an AI that learned to fight.*

---

## About the Game

**Gradient Gladiators** is a top-down 3D arena combat game built in **Java** with **jMonkeyEngine**.

You play as a lone Knight trapped in an ancient arena. Wave after wave, enemies attack, but these are not normal scripted enemies. Every enemy is controlled by a neural network trained with **Reinforcement Learning (RL)**. Nobody programmed them to charge, dodge or keep their distance. They **learned** it by fighting thousands of battles against each other and against simulated players.

Survive four waves, choose upgrades between waves, and defeat the **Iron Brute** boss to win.

### Why this project?
This project combines three areas:

| Area | What it shows |
|---|---|
| **Object-Oriented Design** | A clean class structure using around 10 design patterns (Strategy, State, Observer, Factory, Decorator, Proxy, Facade, Adapter, Object Pool, Template Method) |
| **Reinforcement Learning** | Enemies trained with PPO through a custom **Java ↔ Python** training bridge, then run in-game with ONNX |
| **Data Engineering** | Gameplay telemetry logged and analysed in a dashboard |

### Key Features
- **RL-powered enemies**, each with a different personality created by its reward design
- **Brain View mode**: see what the AI "sees" (raycasts, targets, decisions) in real time
- **Upgrade cards** that stack between waves
- **Low-poly 3D visuals** with dynamic lighting, shadows and particle effects
- **Gameplay analytics dashboard** built from in-game telemetry

---

## Characters

### The Hero

#### The Knight
A heavily armoured warrior who relies on timing, dodging and smart positioning.

| Stat | Value |
|---|---|
| Health | 100 HP |
| Speed | Medium |
| Style | Close-range melee |

| Ability | Description | Damage | Cooldown |
|---|---|---|---|
| **Sword Swing** | Swings in a front arc (2 m range, 90°) | 20 | 0.5 s |
| **Dash** | Quick 5 m dash; invincible while dashing | – | 2 s |
| **Shield Bash** | Pushes enemies back 3 m | 10 | 8 s |

---

### The Enemies

Every enemy uses the **same senses and actions**. Only their **rewards** are different, and that is what gives each one its personality.

#### Brute
*"Hit hard, hit first."*

A big, aggressive melee fighter. It learned to close the distance quickly, attack, and stay out of the way of the Knight's shield bash.

| Stat | Value |
|---|---|
| Health | 60 HP |
| Attack | 15 damage, melee (1.5 m) |
| Speed | Medium |
| Learned behaviour | Charge → attack → sidestep |
| Rewarded for | Hitting the player, finishing fights fast |

#### Archer
*"Never let them get close."*

A fragile but fast ranged fighter. It learned to keep its distance, shoot from a safe range, and run away when the Knight gets close.

| Stat | Value |
|---|---|
| Health | 40 HP |
| Attack | 10 damage, arrow (15 m range) |
| Speed | Fast |
| Learned behaviour | Kite: retreat → turn → shoot |
| Rewarded for | Hitting from long range, staying in its "comfort zone" (8–12 m) |

---

### The Boss

#### Iron Brute
*"The arena's champion."*

A giant armoured Brute whose brain was trained much longer, against upgraded players. It is slower than a normal Brute but hits much harder, and it has a ground-slam attack.

| Stat | Value |
|---|---|
| Health | 300 HP |
| Attacks | 25 damage melee + ground slam (area damage) |
| Speed | Slow |
| Learned behaviour | Waits for the player's mistake, then punishes it |

---

## Game Rules

### Goal
- **Win:** survive all 4 waves and defeat the Iron Brute in wave 5.
- **Lose:** the Knight's health reaches 0. The run ends and starts again from wave 1.

### Controls
| Action | Key |
|---|---|
| Move | `W` `A` `S` `D` |
| Aim | Mouse |
| Sword Swing | Left Click |
| Shield Bash | Right Click |
| Dash | `Space` |
| Brain View (toggle) | `B` |
| Pause | `Esc` |

### Health & Damage
- The Knight starts with **100 HP**.
- Health **does not refill** between waves. Only upgrades can heal.
- An attack deals damage only when its hit area touches a target.
- After being hit, a character is **invincible for 0.3 seconds**.

### Waves
| Wave | Enemies |
|---|---|
| 1 | 3 Brutes |
| 2 | 2 Brutes + 2 Archers |
| 3 | 3 Brutes + 3 Archers |
| 4 | 4 Brutes + 4 Archers |
| 5 | **Iron Brute** + 2 Archers |

- A wave ends when all enemies are defeated.
- After each wave (1–4), the player chooses **1 of 3 random upgrade cards**.

### Upgrade Cards
| Card | Effect |
|---|---|
| **Sharp Blade** | +25% sword damage |
| **Iron Skin** | +20 max HP and heal 20 HP |
| **Swift Feet** | +15% movement speed |
| **Double Dash** | Dash twice before the cooldown starts |
| **Vampire Edge** | Heal 2 HP for every sword hit |
| **Heavy Bash** | Shield bash cooldown −3 s |

Upgrades **stack**, so the same card can be picked more than once.

### Scoring
| Event | Points |
|---|---|
| Brute defeated | 10 |
| Archer defeated | 15 |
| Iron Brute defeated | 100 |
| Remaining HP at the end | HP × 2 |

The results screen shows your **score, time, accuracy and damage taken**.

### The Arena: Forest Ruins
- A round arena about **30 m wide**.
- **4 stone pillars** block movement and arrows, so use them for cover.
- Invisible walls at the edge keep everyone inside.

> All values (HP, damage, cooldowns, speeds) are loaded from a config file and may change during balancing.

---

## Tech Stack

| Part | Technology |
|---|---|
| Game | Java 21, jMonkeyEngine 3.9, Gradle |
| Physics | Minibullet |
| RL training | Python, Gymnasium, Stable-Baselines3 (PPO) |
| Java ↔ Python bridge | TCP sockets + JSON |
| In-game AI inference | ONNX Runtime (Java) |
| Testing | JUnit 5 |
| Analytics | pandas, Streamlit |
| Assets | Free low-poly packs (KayKit / Quaternius / Kenney) |
