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


import * as runtime from '../runtime';
import {
    Todo,
    TodoFromJSON,
    TodoToJSON,
    WriteTodo,
    WriteTodoFromJSON,
    WriteTodoToJSON,
} from '../models';

export interface GetTodoRequest {
    id: number;
}

export interface UpdateTodoRequest {
    id: number;
    WriteTodo: WriteTodo;
}

/**
 * no description
 */
export class ToDosApi extends runtime.BaseAPI {

    /**
     * Get a specific todo item.
     */
    async getTodoRaw(requestParameters: GetTodoRequest): Promise<runtime.ApiResponse<Todo>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling getTodo.');
        }

        const queryParameters: runtime.HTTPQuery = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/todo/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        });

        return new runtime.JSONApiResponse(response, (jsonValue) => TodoFromJSON(jsonValue));
    }

    /**
     * Get a specific todo item.
     */
    async getTodo(requestParameters: GetTodoRequest): Promise<Todo> {
        const response = await this.getTodoRaw(requestParameters);
        return await response.value();
    }

    /**
     * Update todo
     */
    async updateTodoRaw(requestParameters: UpdateTodoRequest): Promise<runtime.ApiResponse<Todo>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling updateTodo.');
        }

        if (requestParameters.WriteTodo === null || requestParameters.WriteTodo === undefined) {
            throw new runtime.RequiredError('WriteTodo','Required parameter requestParameters.WriteTodo was null or undefined when calling updateTodo.');
        }

        const queryParameters: runtime.HTTPQuery = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/todo/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))),
            method: 'PATCH',
            headers: headerParameters,
            query: queryParameters,
            body: WriteTodoToJSON(requestParameters.WriteTodo),
        });

        return new runtime.JSONApiResponse(response, (jsonValue) => TodoFromJSON(jsonValue));
    }

    /**
     * Update todo
     */
    async updateTodo(requestParameters: UpdateTodoRequest): Promise<Todo> {
        const response = await this.updateTodoRaw(requestParameters);
        return await response.value();
    }

}
