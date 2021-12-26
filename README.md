# HellAccountGuard
HAG prevents protected player's account from logging in if IP doesn't match to given.

### Screenshots
![alt text](https://cdn.discordapp.com/attachments/496237265245437982/924612926415990844/unknown.png)
![alt text](https://cdn.discordapp.com/attachments/496237265245437982/924613785317158942/unknown.png)
![alt text](https://cdn.discordapp.com/attachments/496237265245437982/924613919446814720/unknown.png)

## Config.yml
```
cfg-version: 5
language: en
disallow: false
```

## Messages_en.yml
```
doesntmatch: '&cIP адресът ви не съвпада със защитения IP адрес на играча.'
noperm: '&4Нямате разрешение!'
```

### Commands
```
/hag - Display whole commands. - no permission required
/hag add <account> <ip> - Activates the protection and prevents from logging in if IP doesn't match to given. - hag.add
/hag remove <account> <ip> -	Removes the protection for written ip. - hag.remove
/hag remove <account> - Completely removes the protection. - hag.remove
/hag info <account> - Checks if player has protection. - hag.info
/hag save - Saves the ip file to disk. - hag.save
/hag reload - Reloads the config,ip,messages files - hag.reload
```
