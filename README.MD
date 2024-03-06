to build docker image:
    docker-compose build

to save image as archive 
    docker save -o pipisya-telegram-bot.tar pipisya-telegram-bot-image:latest

to copy archive to server scp -i ssh-key.key pipisya-telegram-bot.tar user@host:/home/ubuntu/pipisya-telegram-bot

connect to server ssh -i /ssh-key.key user@host

to load archive 
docker load -i pipisya-telegram-bot.tar

to copy file to server scp -i ssh-key.key docker-compose.yml user@host:/home/ubuntu/pipisya-telegram-bot

to run docker-compose up -d





sudo curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp
sudo chmod a+rx /usr/local/bin/yt-dlp  # Make executable

sudo yt-dlp -U