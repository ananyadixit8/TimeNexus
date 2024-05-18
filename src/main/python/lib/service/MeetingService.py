import requests
from ..helper import MEETINGS_URL


class MeetingService:

    def __init__(self, svc_url=None):
        self.svc_url = MEETINGS_URL

    def get_all_meetings(self):

        request_url = self.svc_url + "/allMeetings"

        response = requests.get(request_url)
        response.raise_for_status()

        return response.json()
