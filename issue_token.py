#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Jul 21 17:12:54 2022

@author: nirmal
"""

import os
from azure.communication.identity import CommunicationIdentityClient, CommunicationUserIdentifier
try:
    print("Azure Communication Services - Issue Access Token")
    #print(os.environ.get('COMMUNICATION_SERVICES_CONNECTION_STRING'))
    connection_string = os.environ["COMMUNICATION_SERVICES_CONNECTION_STRING"]
    client = CommunicationIdentityClient.from_connection_string(connection_string)
    
    identity = client.create_user()
    print("\nCreated an identity with ID: " + identity.properties['id'])
    
    token_result = client.get_token(identity, ["voip"])
    expires_on = token_result.expires_on.strftime("%d/%m/%y %I:%M %S %p")
    
    print("\nIssued an access token with 'voip' scope that expires at " + expires_on + ":")
    print(token_result.token)
except Exception as ex:
    print("Exception--->")
    print(ex)
    
    