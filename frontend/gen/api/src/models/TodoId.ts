// tslint:disable
// eslint-disable
/**
 * To-Do RPG API
 * The API of the To-Do RPG web app. Available for all to use to integrate agains us.
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface TodoId
 */
export interface TodoId {
    /**
     * 
     * @type {number}
     * @memberof TodoId
     */
    id: number;
}

export function TodoIdFromJSON(json: any): TodoId {
    return TodoIdFromJSONTyped(json, false);
}

export function TodoIdFromJSONTyped(json: any, ignoreDiscriminator: boolean): TodoId {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'id': json['id'],
    };
}

export function TodoIdToJSON(value?: TodoId | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'id': value.id,
    };
}

