#!/usr/bin/env python3

import argparse
from datetime import datetime
from src.main.python.lib.service.MeetingService import MeetingService
from src.main.python.lib.service.QueueService import QueueService


def validate_pattern(date_string):

    try:
        date_obj = datetime.strptime(date_string, '%y%m%d')
        return True
    except ValueError:
        raise ValueError(f"Invalid value: {date_string}. Value must be in format YYmmdd")


def arg_parser():

    parser = argparse.ArgumentParser(
        description='Schedule reminders for meetings that are supposed to happen today.'
    )

    parser.add_argument('--date',
                        type=validate_pattern,
                        default=datetime.now().strftime('%y%m%d'),
                        help='Date for which meeting is to be scheduled in format YYmmdd.')
    parser.add_argument('--svc_url',
                        type=str,
                        default=None,
                        help='Svc URL of the instance.')

    return parser.parse_args()


def get_date_from_string(date_string):

    return datetime.strptime(date_string, '%Y-%m-%dT%H:%M:%S.%f%z')


def filter_meeting_ids(meetings, target_date):

    target_datetime = datetime.strptime(target_date, '%y%m%d')
    filtered_meeting_ids = []
    for meeting in meetings:
        if get_date_from_string(meeting["meetingTime"]) == target_datetime:
            filtered_meeting_ids.append(meeting["meetingId"])

    return filtered_meeting_ids


def main():

    args = arg_parser()
    meeting_date = args.date
    svc_url = args.svc_url

    meeting_service = MeetingService(svc_url)
    queue_service = QueueService(svc_url)

    all_meetings = meeting_service.get_all_meetings()
    meeting_ids_on_date = filter_meeting_ids(all_meetings, meeting_date)

    for meeting_id in meeting_ids_on_date:
        response = queue_service.insertMeetingInExecutionQueue(meeting_id)
        print(f"Inserted meeting in Redis: {response}")


if __name__ == "__main__":
    main()
