#! python -B
from slackbot.bot import Bot
import logging
logging.basicConfig()

def main():
    bot = Bot()
    print("*running*")
    bot.run()


if __name__ == "__main__":
    main()
