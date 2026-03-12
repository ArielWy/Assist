# Assist

Assist is a lightweight Minecraft support ticket plugin that allows players to request help from staff using a simple command system. Staff can claim requests, communicate with players, teleport to them, and close tickets when resolved.

## Features

### 🎮 Player Features
- **Request Assistance**: Players can submit help requests with descriptions
- **Real-time Notifications**: Get notified when staff claim or respond to requests
- **Direct Messaging**: Communicate directly with assigned staff members
- **Request Tracking**: Track the status of your assistance request in real-time

### 👥 Staff Features
- **Manage Requests**: Claim and close assistance requests efficiently
- **Instant Notifications**: Receive instant notifications when players request help
- **Teleport to Players**: Quickly teleport to players who need assistance
- **Direct Communication**: Send private messages to players via the plugin
- **Toggle Notifications**: Temporarily or permanently disable assist notifications
- **Request Overview**: See pending assistance requests with player information
- **Close with Reason**: Provide closure reasons when finishing assistance

### ⚙️ Admin Features
- **Permission Management**: Fine-grained permission system for role-based access
- **Configuration Files**: Customizable config, messages, and commands
- **Plugin Reload**: Reload configuration without restarting the server
- **Colored Messages**: Support for MiniMessage color formatting in all messages
- **Permanent Toggles**: Admin-only permanent notification disable list

## Commands

### Player Commands

#### `/assist request <description>`
Send an assistance request to online staff members.

**Example:**
```
/assist request I'm stuck in the nether and need help
```

**Requires:** `assist.request` permission (default: everyone)

---

### Staff Commands

#### `/assist handle claim <player>`
Claim an open assistance request from a specific player.

**Example:**
```
/assist handle claim Steve
```

**Requires:** `assist.handle.claim` permission (default: OP)

---

#### `/assist handle close [reason]`
Close your currently claimed assistance request with an optional reason.

**Example:**
```
/assist handle close Player was teleported to spawn
```

**Requires:** `assist.handle.close` permission (default: OP)

---

#### `/assist handle msg <message>`
Send a direct message to the player you're assisting.

**Example:**
```
/assist handle msg I'm on my way to help you!
```

**Requires:** `assist.handle.msg` permission (default: OP)

---

#### `/assist handle tp`
Teleport to the player you're currently assisting.

**Example:**
```
/assist handle tp
```

**Requires:** `assist.handle.tp` permission (default: OP)

---

#### `/assist toggle`
Toggle whether you want to receive assistance notifications (temporary or permanent).

**Example:**
```
/assist toggle
```

**Requires:** `assist.toggle` permission (default: OP)

---

#### `/assist toggle permanent [on|off]`
Permanently enable or disable assistance notifications (admin only).

**Example:**
```
/assist toggle permanent on
```

**Requires:** `assist.toggle.permanent` permission (default: OP)

---

### Admin Commands

#### `/assist reload`
Reload the plugin configuration files without restarting the server.

**Example:**
```
/assist reload
```

**Requires:** `assist.reload` permission (default: OP)

---

#### `/assist help`
Display available assistance commands and their usage.

**Example:**
```
/assist help
```

---

## Permissions

| Permission | Description | Default |
|-----------|-------------|---------|
| `assist.request` | Send an assistance request | Everyone |
| `assist.handle` | Handle assistance requests (parent) | OP |
| `assist.handle.claim` | Claim an assistance request | OP |
| `assist.handle.close` | Close a claimed request | OP |
| `assist.handle.msg` | Send direct messages to players | OP |
| `assist.handle.tp` | Teleport to assisted players | OP |
| `assist.toggle` | Toggle receiving notifications | OP |
| `assist.toggle.permanent` | Permanently modify notification settings | OP |
| `assist.notify` | Receive assist notifications | OP |
| `assist.reload` | Reload the plugin configuration | OP |
| `assist.*` | Access all plugin permissions | OP |

## Installation

### Requirements
- **Minecraft Server**: 1.21+ (Paper compatible)
- **Java**: Version 21 or higher

### Steps

1. **Download** the latest `Assist.jar` release
2. **Place** the JAR file in your server's `plugins/` directory
3. **Restart** your server
4. **Configure** the plugin using the generated config files in `plugins/Assist/`
5. **Set Permissions** for your staff members

## Configuration

The plugin generates three configuration files in the `plugins/Assist/` directory:

### `config.yml`
Main plugin configuration.

```yaml
# Assist Plugin Configuration

toggle:
  permanentDisabled: []  # List of players who have permanently disabled notifications
```

---

### `messages.yml`
Customizable message strings with MiniMessage support.
Easily craft messages using the [Adventure Web UI](https://webui.advntr.dev/)

```yaml
messages:
  general:
    onlyStaff: "<red>Only staff players can use this command."
    onlyPlayers: "<red>Only players can use this command."
    noPermission: "<red>You do not have permission to do that!"
    unknownSubcommand: "<red>Unknown subcommand: <gray>%SUBCOMMAND%</gray>"
    reloadSuccess: "<green>Assist plugin reloaded successfully."

  request:
    usage: "<red>Usage: /assist request <message>"
    success: "<green>Your assistance request has been sent to the staff."
    notifyStaff: "<yellow>%PLAYER% needs assist"
    claimedByStaff: "<green>Your request has been claimed by <yellow>%STAFF%</yellow><green>."
    closedByStaff: "<green>Your request has been closed by <yellow>%STAFF%</yellow>."
    closedReason: "<gray>Reason: %DESCRIPTION%"
    broadcast: "<yellow>[Assist] <white>%PLAYER% <gray>needs help: <aqua>%MESSAGE%"

  handle:
    usage: "<red>Usage: /assist handle <gold>[%SUBCOMMANDS%]</gold> [args]"
    claimSuccess: "<green>You have claimed the request from <yellow>%PLAYER%</yellow>."
    closeSuccess: "<green>You closed the request from <yellow>%PLAYER%</yellow>."
    helpTip: "<gray>Type <yellow>/assist help</yellow><gray> to see available actions."

  msg:
    staffToPlayer: "<gold>[Assist → %PLAYER%]</gold> <white>%MESSAGE%"
    playerToStaff: "<gold>[Assist]</gold> <yellow>%STAFF%:</yellow> <white>%MESSAGE%"

  toggle:
    temporarilyEnabled: "<green>Assist notifications temporarily enabled.</green>"
    temporarilyDisabled: "<red>Assist notifications temporarily disabled.</red>"
    permanentlyEnabled: "<green>Assist notifications permanently enabled.</green>"
    permanentlyDisabled: "<red>Assist notifications permanently disabled.</red>"
```

## Usage Examples

### Player Requesting Help

```
Player: /assist request I need help finding the village
Server: Your assistance request has been sent to the staff.

Staff receives notification:
[Assist] PlayerName needs help: I need help finding the village
```

### Staff Responding

```
Staff: /assist handle claim PlayerName
Server: You have successfully claimed the request from PlayerName.

Staff: /assist handle msg I'll help you find it!
Server: [Assist → PlayerName] I'll help you find it!

Staff: /assist handle tp
Server: Teleported to PlayerName

(After helping the player)

Staff: /assist handle close Found the village at coordinates X Y Z
Server: You closed the request from PlayerName with description: Found the village at coordinates X Y Z
```

### Managing Notifications

```
Staff: /assist toggle
Server: Assist notifications temporarily disabled.

(Later)

Staff: /assist toggle
Server: Assist notifications temporarily enabled.

Admin: /assist toggle permanent off
Server: Assist notifications permanently disabled.
```

## Troubleshooting

### Players can't use `/assist request`
- Ensure the player has the `assist.request` permission
- Check that the plugin loaded successfully: `/plugins`

### Staff don't receive notifications
- Verify staff have the `assist.notify` permission
- Check if notifications have been toggled off: `/assist toggle`
- Confirm staff haven't permanently disabled notifications

### Commands not working
- Ensure you have the correct permission node
- Try reloading with `/assist reload`
- Check the console for error messages

### Messages not displaying correctly
- Verify color codes in `messages.yml` are correct
- Ensure the YAML file is properly formatted

## Support & Feedback

For issues, feature requests, or questions:
- **GitHub Issues**: [Report an issue](https://github.com/ArielWy/Assist/issues)
- **GitHub Discussions**: [Start a discussion](https://github.com/ArielWy/Assist/discussions)

## License

This project is open source. See the LICENSE file for details.

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Credits

**Author**: olios

Built with ❤️ for the Minecraft community using:
- [Kotlin](https://kotlinlang.org/)
- [Paper API](https://papermc.io/)
- [Maven](https://maven.apache.org/)

---

**Last Updated**: 2026-03-12
