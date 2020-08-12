[![Build Status](https://travis-ci.com/jvmusin/SocialStalker.svg?branch=master)](https://travis-ci.com/jvmusin/SocialStalker)
# The Social Stalker Project

## About

The project helps you to watch for changes in other people friend lists.

At the moment it supports [vk.com](https:://vk.com) and [instagram.com](https:://instagram.com).  
In VK, it's possible to watch for people friends and followers.  
In Instagram, it's possible to watch for people followers and followings (those who follow this people).  
It uses a Telegram bot to notify about updates and to add or remove people to watch for.

Why do you even need it?  
You know, it's kinda useful to know if someone has a new friend or if someone tries to hide people from his friend list,
which is sometimes very annoying.  
It looks like this guy wants to hide his passion from you.  
You SHOULD know about such situations. Such people shouldn't appear in your life. Be careful. Love yourself.  
You will always see new followers and, what's more interesting, new followings! The people, whom your target follow.

Moreover, you don't need to maintain an application. Just host it on Heroku and forget about it.  
You will always receive updates through telegram, and you can configure almost anything you need through telegram.

## Configuring

To fully configure an application, you will need to configure the database, VK, Instagram and Telegram.

You can find all the configuration files in `src/main/resources`.

Also remember, that you can always use environment variables instead of properties in `.yml` files.
Environment variables have higher priority.

### Configuring database

The project uses Postgres inside. All you have to do is to install schema from `sql-scripts/schema.sql`
to your database and set your database username, password and connection string in `application.yml`,
but in most cases you don't have to change it.

### Configuring Telegram

To configure Telegram, do the following:
1. Create a telegram bot using [@BotFather](https://t.me/BotFather) bot and
put its token in `application-dev.yml` and `application-production.yml`under `telegram.bot-token` keys.
You can also create two bots - one for testing and one for production, as I do. If you decide to do it,
put testing token in `application-dev.yml` and production token in `application-production.yml`.
2. Set `telegram.admin-uid` in `application.yml` to your actual id.
You can ask for it from [@userinfobot](https://t.me/userinfobot).
3. You're great!

### Configuring VK

To configure VK, do the following:
1. Create a new VK application [here](https://vk.com/editapp?act=create), choose Standalone App.
2. Obtain an access token (described it right after this section).
3. Set `vk.user-id` in `application-vk.yml` to your id.
You can find it in [account settings](https://vk.com/settings) if you click on `Change Profile URL`.
4. Set `vk.user-access-token` in `application-vk.yml` to your access token, obtained in step 2.
5. You're great! (almost, you probably still need to obtain an access token)

#### Obtaining access token

At first, remember your VK App ID. It's shown at the top of the VK Application settings page.

Open your VK application settings and add `Authorized redirect URI` as `http://localhost/doAuth`.

Then, make next query to VK API with `YOUR_APP_ID` equal to your VK App ID.

```
https://oauth.vk.com/authorize?
client_id=YOUR_APP_ID&
display=page&
redirect_uri=http://localhost/doAuth&scope=offline
&response_type=token
&v=5.107
```

You will be redirected to something like this:

```
http://localhost/doAuth
#access_token=49149fff5277aa1f34cff2864981865d208037cfd19db5ee6f988648b5928f8461eb0c38c69b8e7e78e8
&expires_in=0&user_id=39666902
```

The access token from this line is an access token you have to use in settings.

### Configuring Instagram

To configure Instagram, do the following:
1. Set environment variables `instagram.username` and `instagram.password` to your actual login and password in instagram.
If you are brave enough, you can put it in `application-instagram.yml` directly.
2. Enable two-factor authentication in [account settings](https://www.instagram.com/accounts/privacy_and_security).
We will need it to authenticate an application.
3. When you start an application for the first time, it will try to authenticate you.
It will send an SMS to your phone, and you'll have to enter it in a console. Then you'll be authenticated.
4. You' re great! (I promise not to steal your login and password, though it's so tasty...)

## Using an application

After you fully configured an application, run it and enjoy!

You can send messages to your bot and add people to watch.

You can configure periods between updates in `application-dev.yml` and `application-production.yml` files.